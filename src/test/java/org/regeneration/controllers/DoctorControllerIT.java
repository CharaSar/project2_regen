package org.regeneration.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.regeneration.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {Application.class, SpringSecurityWebAuxTestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

@AutoConfigureMockMvc
public class DoctorControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithUserDetails("doc1")
    public void getCitizenById() throws Exception {
        String expected = "{\"citizenId\":3,\"firstname\":\"Maria\",\"lastname\":\"Konstantinou\",\"email\":\"mariakon@gmail.com\",\"phone\":\"6983035874\",\"ssn\":\"11223345678\",\"user\":{\"userId\":23,\"username\":\"cit3\",\"role\":\"CITIZEN\"}}";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctor/citizen/3")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @WithUserDetails("doc1")
    public void getDoctorAppointments() throws Exception {
        String expected = "[{\"appointmentId\":1,\"date\":\"2018-12-03\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":2,\"date\":\"2018-12-04\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":3,\"date\":\"2018-12-05\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":4,\"date\":\"2018-12-06\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":5,\"date\":\"2018-12-07\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":6,\"date\":\"2018-12-08\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":7,\"date\":\"2018-12-09\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":8,\"date\":\"2018-12-10\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":9,\"date\":\"2018-12-11\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":10,\"date\":\"2018-12-12\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":11,\"date\":\"2018-12-13\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":12,\"date\":\"2018-12-14\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":13,\"date\":\"2018-12-15\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":14,\"date\":\"2018-12-16\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":15,\"date\":\"2018-12-17\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":16,\"date\":\"2018-12-18\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":17,\"date\":\"2018-12-19\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":18,\"date\":\"2018-12-20\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":19,\"date\":\"2018-12-21\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":20,\"date\":\"2018-12-22\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":2,\"firstname\":\"Vasiliki\",\"lastname\":\"Georgiou\",\"email\":\"vasilikigeor@gmail.com\",\"phone\":\"6978912569\",\"ssn\":\"98765432101\",\"user\":{\"userId\":22,\"username\":\"cit2\",\"role\":\"CITIZEN\"}}}]";
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctor/appointments")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @WithUserDetails("doc1")
    public void getDoctorAppointmentById() throws Exception {
        String expected = "{\"appointmentId\":3,\"date\":\"2018-12-05\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}}";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctor/appointment/3")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @WithUserDetails("doc1")
    public void getCitizenByIdExpectNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctor/citizen/30000")
                .accept(MediaType.ALL))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCitizenByIdExpectUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/doctor/citizen/30000")
                .accept(MediaType.ALL))
                .andExpect(status().isUnauthorized());
    }
}