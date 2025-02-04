package net.engineeringdigest.journalApp.scheduler;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.UserEntity;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.repository.UserRepoImpl;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepo;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 30 9 * * SUN")
    public void fetchUsersAndSendSaMail(){
        List<UserEntity> userForSA = userRepo.getUserForSA();
        for(UserEntity user:userForSA)
        {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : sentiments)
            {
                if(sentiment != null)
                {
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment , 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCnt = 0;
            for(Map.Entry<Sentiment,Integer> entry:sentimentCounts.entrySet())
            {
                if(entry.getValue() > maxCnt)
                {
                    maxCnt = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if(mostFrequentSentiment != null)
            {
                emailService.sendMail(user.getEmail() , "Sentiment For Last & days" , mostFrequentSentiment.toString());
            }
        }

    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearCache()
    {
        appCache.init();
    }
}
