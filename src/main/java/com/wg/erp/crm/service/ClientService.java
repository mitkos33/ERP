package com.wg.erp.crm.service;


import com.wg.erp.crm.model.dto.ClientAddDTO;
import com.wg.erp.crm.model.entity.Client;
import com.wg.erp.crm.repository.ClientRepository;
import com.wg.erp.model.entity.User;
import com.wg.erp.model.user.ErpUserDetailsModel;
import com.wg.erp.repository.UserRepository;
import com.wg.erp.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ClientService(ClientRepository clientRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<Client> getAllClients() {
        return this.clientRepository.findAll();
    }

    public long getCountClients() {
        return this.clientRepository.count();
    }

    public void addClient(ClientAddDTO client, String email) {
        Client clientEntity = mapClientAddDTO(client);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            clientEntity.setCreatedBy(user.get());
        }
        this.clientRepository.save(clientEntity);
    }

    public Client mapClientAddDTO(ClientAddDTO clientAddDTO) {
        return  modelMapper.map(clientAddDTO, Client.class);
    }

    public ClientAddDTO getClientById(Long id) {
        return this.clientRepository.findById(id)
                .map(client -> modelMapper.map(client, ClientAddDTO.class))
                .orElse(ClientAddDTO.empty());
    }

    public void updateClient(ClientAddDTO clientAddDTO, Long id) {
        Client client = this.clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client with id " + id + " not found!"));
        modelMapper.map(clientAddDTO, client);
        this.clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        this.clientRepository.deleteById(id);
    }
}
