package com.unigpt.plugin.Repository;


import com.unigpt.plugin.model.Plugin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PluginRepository extends JpaRepository<Plugin, Integer> {

    List<Plugin> findAllByOrderByIdDesc();

    Plugin findById(int id);
}
