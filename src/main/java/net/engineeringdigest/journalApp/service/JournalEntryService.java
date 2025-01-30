package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public void SaveEntry(JournalEntry myje, String userName)
    {
        try {
            UserEntity user = userService.findByUserName(userName);
            myje.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(myje);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch (Exception e)
        {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry.",e);
        }


    }
    public void SaveEntry(JournalEntry myje)
    {
        journalEntryRepository.save(myje);

    }
    public List<JournalEntry>getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id)
    {
        return journalEntryRepository.findById(String.valueOf(id));
    }

    public void DeleteById(ObjectId id, String userName)
    {
        UserEntity user = userService.findByUserName(userName);
        userService.saveNewUser(user);
        user.getJournalEntries().removeIf(x-> x.getId().equals(id));
        journalEntryRepository.deleteById(String.valueOf(id));
    }
}
