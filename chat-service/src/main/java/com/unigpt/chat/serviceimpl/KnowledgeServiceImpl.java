package com.unigpt.chat.serviceimpl;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.unigpt.chat.config.BasicConfig;
import com.unigpt.chat.dto.ResponseDTO;
import com.unigpt.chat.model.Bot;
import com.unigpt.chat.model.User;
import com.unigpt.chat.repository.BotRepository;
import com.unigpt.chat.repository.UserRepository;
import com.unigpt.chat.service.KnowledgeService;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

// pgvector
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    static int maxSegmentSizeInChar = 300, dimension = 384;
    private final BotRepository botRepository;
    private final UserRepository userRepository;
    private final EmbeddingModel embeddingModel;

    private final BasicConfig basicConfig;

    public KnowledgeServiceImpl(
            BotRepository botRepository,
            UserRepository userRepository,
            BasicConfig basicConfig) {
        this.botRepository = botRepository;
        this.userRepository = userRepository;
        this.embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        this.basicConfig = basicConfig;
    }

    public String extractText(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.toLowerCase().endsWith(".pdf")) {
            return extractTextFromPdf(file);
        } else if (fileName != null && fileName.toLowerCase().endsWith(".txt")) {
            return extractTextFromTxt(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileName);
        }
    }

    @Override
    public EmbeddingStore<TextSegment> createEmbeddingStore(Integer botId) {
        return PgVectorEmbeddingStore.builder()
                .host(basicConfig.POSTGRES_HOST)
                .port(basicConfig.POSTGRES_PORT)
                .database(basicConfig.POSTGRES_DB)
                .user(basicConfig.POSTGRES_USERNAME)
                .password(basicConfig.POSTGRES_PASSWORD)
                .table("bot" + botId)
                .dimension(dimension)
                .build();
    }

    @Override
    public ResponseDTO uploadFile(Integer userId, Boolean isAdmin, Integer botId, MultipartFile file)
            throws AuthenticationException {
        Bot bot = botRepository.findById(botId)
                .orElseThrow(() -> new NoSuchElementException("Bot not found for ID: " + botId));

        User user = userRepository.findById(userId).orElseThrow(() -> new AuthenticationException("User not found"));

        if (!isAdmin && !bot.getCreator().equals(user)) {
            throw new AuthenticationException("Unauthorized to upload file.");
        }

        try {
            Document document = new Document(extractText(file));
            DocumentSplitter splitter = DocumentSplitters.recursive(
                    maxSegmentSizeInChar,
                    0);
            List<TextSegment> textSegmentList = splitter.split(document);

            EmbeddingStore<TextSegment> embeddingStore = createEmbeddingStore(botId);
            for (TextSegment segment : textSegmentList)
                embeddingStore.add(
                        embeddingModel.embed(segment).content(),
                        segment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }

        return new ResponseDTO(true, "Successfully upload " + file.getOriginalFilename());
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        }
    }

    private String extractTextFromTxt(MultipartFile file) throws IOException {
        StringBuilder textBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textBuilder.append(line).append(System.lineSeparator());
            }
        }
        return textBuilder.toString();
    }
}
