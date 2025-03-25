package org.example.client;

import org.example.Topic.Answer;
import org.example.Topic.Topic;
import org.example.Topic.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateVote {
    Scanner scanner = new Scanner(System.in);
    private String name, description,  response_option;
    private int namber_pa;
    private List<Answer> answers = new ArrayList<>();

    public void createVote(String topic_client, List<Topic> topics){
       for (Topic topic : topics){
           if (topic.getName_topic().equals(topic_client)){
               System.out.println("Name: ");
               name = scanner.nextLine();
               topic.setName_vote(name);

               System.out.println("Description: ");
               description = scanner.nextLine();
               Vote vote = new Vote(description,0,null);
               topic.setVotes(vote);

               System.out.println("number of possible answers");
               namber_pa = scanner.nextInt();
               topic.getVotes().setCount(namber_pa);

               System.out.println("response options");
               String s = scanner.nextLine();
               for (int i = 1; i<=namber_pa; i++){
                   System.out.println("Ответ " + i);
                   response_option = scanner.nextLine();
                   answers.add(new Answer(response_option,0));
                   topic.getVotes().setAnswer(answers);
                   System.out.println("Ответ записан");
               }
           }else {
               System.out.println("Create Vote error");
           }
           break;
       }
        System.out.println("Новое голосование создано ");
    }

}
