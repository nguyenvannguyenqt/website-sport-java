package com.nhom07.DAMH_LTUD.service;

import com.nhom07.DAMH_LTUD.NotFoundByIdException;
import com.nhom07.DAMH_LTUD.model.ContactUser;
import com.nhom07.DAMH_LTUD.model.Country;
import com.nhom07.DAMH_LTUD.repository.ContactUserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@NoArgsConstructor
public class ContactUserService {

    @Autowired
    private ContactUserRepository contactUserRepository;


    public List<ContactUser> getListContacts()
    {
        return contactUserRepository.findAll();
    }
    public ContactUser getById(Long id) throws NotFoundByIdException {
        Optional<ContactUser> optional = contactUserRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundByIdException("Không tìm thấy id: " + id);
    }

    public void addContactUser(ContactUser contactUser) {
        contactUserRepository.save(contactUser);
    }

    public void deleteContactUser(Long id) {
        if (!contactUserRepository.existsById(id)) {
            throw new IllegalStateException("Không tìm thấy id: " + id);
        }
        contactUserRepository.deleteById(id);
    }
    public void updateContactUser(@NotNull ContactUser contactUser) {
        ContactUser contactUserExists = contactUserRepository.findById(contactUser.getId())
                .orElseThrow(() -> new IllegalStateException("ContactUser with id: " + contactUser.getId() + " does not exist."));
        contactUserRepository.save(contactUserExists);
    }

}
