package com.stringauto.controller;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.stringauto.model.User;
import com.stringauto.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    UserService userService;
    
    @InjectMocks
    private UserController userController;
 
    
    
    @Before
    public void setup() {
    		MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    
    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
      throws Exception {
        
    	User user = new User();
    	
    	
         user.setId(1);
         user.setUsername("lphilips");
         user.setPassword("test");
        
        when(this.userService.findById(1)).thenReturn(user);
        
        mockMvc.perform(get("/api/users/{id}", "1"))
                .andExpect(status().isOk());
                  
        
        
        
        
        
    }
    
    
    
    
    /*
    
    @Test
    public void testSayHelloWorld() throws Exception {
        this.mockMvc.perform(get("api/users").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk());

    }
    
    
    
    
    
    
    @Test
    public void shouldGet200WhenGivenValidCredentials() throws Exception {

      //  
        
        User user = new User();
        user.setId(1);
        user.setUsername("lphilips");
        user.setPassword("test");
       
        
        Gson gson = new Gson();
        String json = gson.toJson(user);

        this.mvc.perform(get("api/users/1").param("1", "1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        
        
     
        
       // this.mvc.perform(get("/api/login").header("Authorization", "Bearer " + token))
        //        .andExpect(status().is(200));

    }

  
    @Test(expected = ExpiredJwtException.class)
    public void shouldNotGet200WhenGivenInvalidOldToken() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(1L); // set time back to 1970
        String token = tokenHelper.generateToken(new UserDetailsDummy("test-user").getUsername());
        DateTimeUtils.setCurrentMillisSystem(); // back to now
        ResultActions action = null;
        this.mvc.perform(get("/auth/refresh").header("Authorization", "Bearer " + token));
    }
    
    */

}