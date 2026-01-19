package com.joaodev.user_request_sb.reader;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.joaodev.user_request_sb.domain.ResponseUser;
import com.joaodev.user_request_sb.dto.UserDTO;

@Configuration
public class FetchUserDataReaderConfig implements ItemReader<UserDTO> {

    public static Logger logger = LoggerFactory.getLogger(FetchUserDataReaderConfig.class);

    private final String BASE_URL = "http://localhost:8081";

    private RestTemplate restTemplate = new RestTemplate();

    private int page = 0;

    private List<UserDTO> users = new ArrayList<>();

    private int userIndex = 0;

    @Value("${chunkSize:10}")
    private int chunkSize;

    @Value("${pageSize:10}")
    private int pageSize;

    @Override
    public UserDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        UserDTO user;
        if (userIndex < users.size()) {        
            user = users.get(userIndex);
        }else{
            user = null;
        }
        userIndex ++;
        return user;
    }

    private List<UserDTO> fetchUserDataFromAPI() {
        String uri = String.format(BASE_URL + "/clients/pagedData?page=%d&size=%d", getPage(), pageSize);
        try {
            logger.info("[READER STEP] Request uri: " + uri);
            ResponseEntity<ResponseUser> response = restTemplate.exchange(uri, HttpMethod.GET, null, 
                    new ParameterizedTypeReference<ResponseUser>(){});
            
            if (response.getBody() != null) {
                return response.getBody().getContent();
            }
            return new ArrayList<>();
        } catch (Exception e) {
            logger.error("ERRO AO CHAMAR API: " + e.getMessage());
            throw e; 
        }
    }

    public int getPage(){
        return page;
    }

    @BeforeChunk
    public void beforeChunck(ChunkContext context){
        try {
            for (int i = 0; i < chunkSize; i += pageSize) {
                users.addAll(fetchUserDataFromAPI());
            }
        } catch (Exception e) {
            logger.error("ERRO CRÍTICO NO BEFORE CHUNK: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    @AfterChunk
    public void afterChunk(ChunkContext context){
        logger.info("Final chunk");
        page ++;
        userIndex = 0;
        users = new ArrayList<>();
    }
}
