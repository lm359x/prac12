package org.example.springhomework;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FileHasher {
    @Value("#{springApplicationArguments.nonOptionArgs[0]}")
    private String sourcePath;

    @Value("#{springApplicationArguments.nonOptionArgs[1]}")
    private String destinationPath;
    private String buffer;
    private Boolean Ioexceptionmet;

    @Override
    public String toString() {
        return "FileHasher{" +
                "sourcePath='" + sourcePath + '\'' +
                ", destinationPath='" + destinationPath + '\'' +
                ", buffer='" + buffer + '\'' +
                '}';
    }
    @PostConstruct
    public void init(){
        Ioexceptionmet = false;
        try{
            FileReader reader = new FileReader(sourcePath);
            System.out.println("file found");
            buffer = "";
            int c;
            while((c=reader.read())!=-1){
                buffer+=(char)(c+1);
            }
        }
        catch(IOException ex){
            System.out.println("file not found");
            Ioexceptionmet = true;
        }

    }
    @PreDestroy
    public void Cleanup(){
        System.out.println("destroy");
        try(FileWriter writer = new FileWriter(destinationPath,false)){
            if(Ioexceptionmet){
                writer.write("null");
                return;
            }
            writer.write(buffer);

        }
        catch(Exception exception){
            System.out.println(exception.getMessage());
        }
    }

}
