package com.wg.erp.crm.service;


import com.wg.erp.crm.model.dto.ClientAddDTO;
import com.wg.erp.crm.model.dto.ClientDetailDTO;
import com.wg.erp.crm.model.entity.Client;
import com.wg.erp.crm.repository.ClientRepository;
import com.wg.erp.model.entity.User;
import com.wg.erp.model.user.ErpUserDetailsModel;
import com.wg.erp.repository.UserRepository;
import com.wg.erp.service.ErpUserDetailService;
import org.modelmapper.ModelMapper;
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

    public List<Client> getAllClients(ErpUserDetailsModel userDetails) {
        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return this.clientRepository.findAll();
        }
        else {
            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            if (user.isEmpty()) {
              throw new IllegalArgumentException("User with email " + userDetails.getUsername() + " not found!");
            }
            return this.clientRepository.findAllByCreatedByOrOwner(user.get(), user.get());
        }
    }

    public List<ClientDetailDTO> getAllClientsDetail() {
        return this.clientRepository.findAll()
                .stream()
                .map(client -> modelMapper.map(client, ClientDetailDTO.class))
                .toList();
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

    public ClientAddDTO getClientById(Long id, ErpUserDetailsModel userDetails) {
        if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return this.clientRepository.findById(id)
                    .map(client -> modelMapper.map(client, ClientAddDTO.class))
                    .orElse(ClientAddDTO.empty());
        }
        else {
            Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
            if (user.isEmpty()) {
                throw new IllegalArgumentException("User with email " + userDetails.getUsername() + " not found!");
            }
            return this.clientRepository.findById(id)
                    .filter(client -> client.getCreatedBy().equals(user.get()) || client.getOwner().equals(user.get()))
                    .map(client -> modelMapper.map(client, ClientAddDTO.class))
                    .orElseThrow(() -> new IllegalArgumentException("Client with id " + id + " not found or you haven't permission!"));
        }
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

    public ClientDetailDTO findById(Long id) {
        return this.clientRepository.findById(id)
                .map(client -> modelMapper.map(client, ClientDetailDTO.class))
                .orElse(null);
    }
}
