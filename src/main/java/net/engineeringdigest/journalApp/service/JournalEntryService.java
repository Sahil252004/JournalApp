package net.engineeringdigest.journalApp.service;



import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    @Transactional
    public boolean DeleteById(ObjectId id, String userName)
    {
        boolean flag = false;
        try {
            UserEntity user = userService.findByUserName(userName);
            flag = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (flag) {
                userService.saveUser(user);

                journalEntryRepository.deleteById(String.valueOf(id));
            }
        } catch (Exception e) {
            log.error("Error ",e);
            throw new RuntimeException("Error occured while deleting the entry",e);
        }
        return flag;


    }
}
