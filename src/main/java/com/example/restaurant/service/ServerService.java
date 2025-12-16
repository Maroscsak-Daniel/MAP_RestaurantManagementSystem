package com.example.restaurant.service;

import com.example.restaurant.model.Server;
import com.example.restaurant.repository.ServerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {

    private final ServerRepository repo;

    public ServerService(ServerRepository repo) {
        this.repo = repo;
    }

    public List<Server> getAll() {
        return repo.findAll();
    }

    public Server getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Server not found: " + id));
    }

    public Server create(Server server) {
        return repo.save(server);
    }

    public Server update(Long id, Server data) {
        Server s = getById(id);
        s.setName(data.getName());
        s.setShift(data.getShift());
        s.setExperienceYears(data.getExperienceYears());
        return repo.save(s);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Page<Server> getAllPaged(String shift, Integer minExp, String sortBy, String dir, Pageable pageable) {
        org.springframework.data.domain.Sort sort = org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.fromString(dir == null ? "ASC" : dir), sortBy == null ? "id" : sortBy);
        Pageable p = org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        if ((shift != null && !shift.isEmpty()) && (minExp != null)) {
            return repo.findByShiftContainingIgnoreCaseAndExperienceYearsGreaterThanEqual(shift, minExp, p);
        } else if (shift != null && !shift.isEmpty()) {
            return repo.findByShiftContainingIgnoreCase(shift, p);
        } else if (minExp != null) {
            return repo.findByExperienceYearsGreaterThanEqual(minExp, p);
        } else {
            return repo.findAll(p);
        }
    }
}
