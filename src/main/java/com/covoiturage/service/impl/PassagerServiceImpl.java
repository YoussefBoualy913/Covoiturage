
package com.covoiturage.service.impl;

import com.covoiturage.dto.passager.PassagerRequest;
import com.covoiturage.dto.passager.PassagerResponse;
import com.covoiturage.entity.Passager;
import com.covoiturage.exception.DuplicateResourceException;
import com.covoiturage.exception.ResourceNotFoundException;
import com.covoiturage.mapper.PassagerMapper;
import com.covoiturage.repository.PassagerRepository;
import com.covoiturage.repository.UserRepository;
import com.covoiturage.service.PassagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PassagerServiceImpl implements PassagerService {

    private final PassagerRepository passagerRepository;
    private final UserRepository userRepository;
    private final PassagerMapper passagerMapper;

    @Override
    @Transactional
    public PassagerResponse create(PassagerRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Cet email est déjà utilisé.");
        }

        Passager passager = passagerMapper.toEntity(request);

        passager.setActive(true);

        Passager savedPassager = passagerRepository.save(passager);

        return passagerMapper.toResponse(savedPassager);
    }

    @Override
    public List<PassagerResponse> findAll() {

        List<Passager> passagers = passagerRepository.findAll();

        return passagerMapper.toResponseList(passagers);
    }

    @Override
    public PassagerResponse findById(UUID id) {

        Passager passager = passagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Passager introuvable avec l'id : " + id
                ));

        return passagerMapper.toResponse(passager);
    }

    @Override
    @Transactional
    public PassagerResponse update(UUID id, PassagerRequest request) {

        Passager passager = passagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Passager introuvable avec l'id : " + id
                ));

        if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new DuplicateResourceException(
                    "Cet email est déjà utilisé."
            );
        }

        passagerMapper.updateFromRequest(request, passager);

        Passager updatedPassager = passagerRepository.save(passager);

        return passagerMapper.toResponse(updatedPassager);
    }

    @Override
    @Transactional
    public void delete(UUID id) {

        Passager passager = passagerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Passager introuvable avec l'id : " + id
                ));

        passager.setActive(false);

        passagerRepository.save(passager);
    }
}
