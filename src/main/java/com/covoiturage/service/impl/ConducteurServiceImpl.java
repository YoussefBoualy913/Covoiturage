package com.covoiturage.service.impl;

import com.covoiturage.dto.conducteur.ConducteurRequest;
import com.covoiturage.dto.conducteur.ConducteurResponse;
import com.covoiturage.entity.Conducteur;
import com.covoiturage.exception.EmailAlreadyExistsException;
import com.covoiturage.exception.LicenseNumberAlreadyExistsException;
import com.covoiturage.mapper.ConducteurMapper;
import com.covoiturage.repository.ConducteurRepository;
import com.covoiturage.repository.UserRepository;
import com.covoiturage.service.ConducteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConducteurServiceImpl implements ConducteurService {

    private final ConducteurRepository conducteurRepository;
    private final UserRepository userRepository;
    private final ConducteurMapper conducteurMapper;

    @Override
    @Transactional
    public ConducteurResponse create(ConducteurRequest request) {


        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Cet email est déjà utilisé.");
        }

        if (conducteurRepository.existsByNumeroPermis(request.numeroPermis())) {
            throw new LicenseNumberAlreadyExistsException("Ce numéro de permis est déjà utilisé.");
        }


        Conducteur conducteur = conducteurMapper.toEntity(request);


        conducteur.setActive(true);

        Conducteur savedConducteur = conducteurRepository.save(conducteur);
        System.out.println(conducteur);
        return conducteurMapper.toResponse(savedConducteur);
    }

    @Override
    public List<ConducteurResponse> findAll() {

        List<Conducteur> conducteurs = conducteurRepository.findAll();

        return conducteurMapper.toResponseList(conducteurs);
    }

    @Override
    public ConducteurResponse findById(Long id) {
        throw new UnsupportedOperationException("");
    }

    @Override
    @Transactional
    public ConducteurResponse update(Long id, ConducteurRequest request) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public List<ConducteurResponse> search(String nomOuEmail) {
        throw new UnsupportedOperationException("");
    }
}
