package com.bikkadit.electronic.store.ElectronicStore.repositories;

import com.bikkadit.electronic.store.ElectronicStore.entities.Category;
import com.bikkadit.electronic.store.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,String> {

    List<Category> findByNameContaining(String keyword);
}
