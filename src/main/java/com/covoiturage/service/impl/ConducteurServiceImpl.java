package com.covoiturage.service.impl;

import com.covoiturage.dto.conducteur.ConducteurRequest;
import com.covoiturage.dto.conducteur.ConducteurResponse;
import com.covoiturage.entity.Conducteur;
import com.covoiturage.exception.ConducteurNotFoundException;
import com.covoiturage.exception.EmailAlreadyExistsException;
import com.covoiturage.exception.LicenseNumberAlreadyExistsException;
import com.covoiturage.mapper.ConducteurMapper;
import com.covoiturage.repository.ConducteurRepository;
import com.covoiturage.repository.UserRepository;
import com.covoiturage.service.ConducteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    public ConducteurResponse findById(UUID id) {

        Conducteur conducteur = conducteurRepository.findById(id).orElseThrow(() -> new ConducteurNotFoundException("Conducteur not found with id: " + id));
        return conducteurMapper.toResponse(conducteur);
    }

    @Override
    @Transactional
    public ConducteurResponse update(UUID id, ConducteurRequest request) {

        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new ConducteurNotFoundException(
                        "Conducteur introuvable avec l'id : " + id
                ));


        if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new EmailAlreadyExistsException(
                    "Cet email est déjà utilisé."
            );
        }


        if (conducteurRepository.existsByNumeroPermisAndIdNot(
                request.numeroPermis(), id)) {
            throw new LicenseNumberAlreadyExistsException(
                    "Ce numéro de permis est déjà utilisé."
            );
        }


        conducteurMapper.updateFromRequest(request, conducteur);


        Conducteur updatedConducteur = conducteurRepository.save(conducteur);

        return conducteurMapper.toResponse(updatedConducteur);
    }

    @Override
    @Transactional
    public void delete(UUID id) {


        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new ConducteurNotFoundException(
                        "Conducteur introuvable avec l'id : " + id
                ));


        conducteur.setActive(false);
         System.out.println("voic le status de conducteur"+conducteur.isActive());
        conducteurRepository.save(conducteur);
    }

}
