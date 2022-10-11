package com.ccsw.tutorial.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.exception.AlreadyExistsException;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientServiceImpl clientServiceImpl;

    public static final Long NOT_EXISTS_CLIENT_ID = 0L;

    @Test
    public void getExistsClientIdShouldReturnClient() {

        Client client = mock(Client.class);
        when(client.getId()).thenReturn(EXISTS_CLIENT_ID);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        Client clientResponse = clientServiceImpl.get(EXISTS_CLIENT_ID);

        assertNotNull(clientResponse);
        assertEquals(EXISTS_CLIENT_ID, client.getId());
    }

    @Test
    public void getNotExistsClientIdShouldReturnNull() {

        when(clientRepository.findById(NOT_EXISTS_CLIENT_ID)).thenReturn(Optional.empty());

        Client client = clientServiceImpl.get(NOT_EXISTS_CLIENT_ID);

        assertNull(client);
    }

    @Test
    public void findAllShouldReturnAllClients() {

        List<Client> list = new ArrayList<>();
        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> clients = clientServiceImpl.findAll();

        assertNotNull(clients);
        assertEquals(1, clients.size());
    }

    public static final String CLIENT_NAME = "CLIENT1";

    @Test
    public void saveNotExistsClientIdShouldInsert() throws AlreadyExistsException {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);

        clientServiceImpl.save(null, clientDto);

        verify(clientRepository).save(client.capture());

        assertEquals(CLIENT_NAME, client.getValue().getName());
    }

    public static final Long EXISTS_CLIENT_ID = 1L;

    @Test
    public void saveExistsClientIdShouldUpdate() throws AlreadyExistsException {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME);

        Client client = mock(Client.class);
        when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

        clientServiceImpl.save(EXISTS_CLIENT_ID, clientDto);

        verify(clientRepository).save(client);
    }

    @Test
    public void deleteExistsClientIdShouldDelete() {

        clientServiceImpl.delete(EXISTS_CLIENT_ID);

        verify(clientRepository).deleteById(EXISTS_CLIENT_ID);
    }

    public static final String EXISTING_CLIENT_NAME = "EXISTING_CLIENT_1";

    @Test
    public void saveExistsClientNameShouldThrowException() {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(EXISTING_CLIENT_NAME);

        try {
            doThrow(new AlreadyExistsException()).when(this.clientService).save(clientDto.getId(), clientDto);
            clientService.save(null, clientDto);
            fail("Exception expected");
        } catch (AlreadyExistsException e) {
            assertEquals(AlreadyExistsException.class, e.getClass());
        }

    }

}