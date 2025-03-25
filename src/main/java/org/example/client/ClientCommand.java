package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Topic.Answer;
import org.example.Topic.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ClientCommand {
    private final ObjectMapper mapper = new ObjectMapper();
    private List<Topic> topics = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private String message;
    private String regex = Pattern.quote("=");

    public void clientCommand() throws JsonProcessingException {
        while (true){
            message = scanner.nextLine();
            String[] mess_arr = message.split(regex);
            Loop:
            switch (mess_arr[0]){
                case "create topic -n":
                    for (Topic topic : topics){
                        if (topic.getName_topic().equals(mess_arr[1])){
                            System.out.println("Раздел с называнием " + mess_arr[1] + " уже существует");
                            break Loop;
                        }
                    }
                    topics.add(new Topic(mess_arr[1],null,null));
                    System.out.println("Раздел " + mess_arr[1] + " создан");
                    break;
                case "view":
                    System.out.println("Список существующих разделов");
                    for (Topic topic : topics){
                        System.out.println(topic.getName_topic());
                    }
                    break;
                case "view -t":
                    if (mess_arr[1].contains("-v")){
                        String topic = null;
                        String vote = null;
                        String[] parts = message.split(" ");
                        for (String part : parts){
                            if(part.startsWith("-t=")){
                                topic = part.substring(3);
                            }else if (part.startsWith("-v=")){
                                vote = part.substring(3);
                            }
                        }
                        for (Topic topic1 : topics){
                            if (topic1.getName_topic().equals(topic) && topic1.getName_vote().equals(vote)){
                                System.out.println("Тема голосования " + topic1.getVotes().getDescription());
                                System.out.println("Варианты ответа " +
                                        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(topic1.getVotes().getAnswer()));
                            break Loop;
                            }
                        }
                    }
                    else {
                        System.out.println("Список голосований в разделе " + mess_arr[1]);
                        for (Topic topic : topics){
                            if (topic.getName_topic().equals(mess_arr[1])){
                                System.out.println(topic.getName_vote());
                            }
                        }
                    }
                    break;
                case "create vote -t":
                    boolean b = false;
                    for (Topic topic : topics){
                        if (topic.getName_topic().equals(mess_arr[1])){
                            b = false;
                            System.out.println("Создать новое голосование в разделе " + mess_arr[1]);
                            CreateVote createVote = new CreateVote();
                            createVote.createVote(mess_arr[1],topics);
                            break Loop;
                        } else  b = true;
                    }
                    if (b) System.out.println("Раздел не существует");
                    break;
                case "vote -t":
                    if (mess_arr[1].contains("-v")){
                        String topic = null;
                        String vote = null;
                        String[] parts = message.split(" ");
                        for (String part : parts){
                            if(part.startsWith("-t=")){
                                topic = part.substring(3);
                            }else if (part.startsWith("-v=")){
                                vote = part.substring(3);
                            }
                        }
                        for (Topic topic1 : topics){
                            List<Answer> answers = new ArrayList<>();
                            if (topic1.getName_topic().equals(topic) && topic1.getName_vote().equals(vote)){
                                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(topic1.getVotes().getAnswer()));
                                System.out.println("Выберете ответ ");
                                int x = scanner.nextInt();
                                int i = 0;
                                for (Answer answer : answers){
                                    if (i == x){
                                        answer.setCount_user(+1);
                                    }
                                    i=i+1;
                                }
                            }
                        }
                    }
                    break;
                case "delete -t":
                    if (mess_arr[1].contains("-v")) {
                        String topic = null;
                        String vote = null;
                        String[] parts = message.split(" ");
                        for (String part : parts) {
                            if (part.startsWith("-t=")) {
                                topic = part.substring(3);
                            } else if (part.startsWith("-v=")) {
                                vote = part.substring(3);
                            }
                        }
                        for (Topic topic1 : topics){
                            if (topic1.getName_topic().equals(topic) && topic1.getName_vote().equals(vote)){
                                topics.remove(topic1);
                            }
                        }
                    }
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("creeeate error");
            }
        }
    }
}
