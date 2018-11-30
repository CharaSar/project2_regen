package org.regeneration.controllers;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = {Application.class, SpringSecurityWebAuxTestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureMockMvc
public class CitizenControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private static String testingAppointmentId;

    @Test
    @WithUserDetails("cit1")
    public void stage1_createCitizenAppointment() throws Exception {
        String toCreate = "{\"doctorId\":1,\"date\":\"2022-08-05\",\"time\":\"08:30\",\"illnessHistory\":\"illHist\",\"notes\":\"Note\"}\n";
        String expected = "\"date\":\"2022-08-05\",\"time\":\"08:30\",\"illnessHistory\":\"illHist\",\"notes\":\"Note\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}}";

        MvcResult m = mockMvc.perform(MockMvcRequestBuilders.post("/api/citizen/appointment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toCreate)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andReturn();
        assertThat(m.getResponse().getContentAsString().contains(expected));

        String[] s = m.getResponse().getContentAsString().split("\"");
        testingAppointmentId = s[2].substring(1, s[2].length()-1);
    }

    @Test
    @WithUserDetails("cit1")
    public void stage2_editAppointment() throws Exception {
        String toEdit = "{\"appointmentId\":"+ testingAppointmentId +",\"date\":\"2022-08-05\",\"time\":\"08:30\",\"illnessHistory\":\"illHist\",\"notes\":\"Notesome\"}";
        String expected = "{\"appointmentId\":"+ testingAppointmentId +",\"date\":\"2022-08-05\",\"time\":\"08:30\",\"illnessHistory\":\"illHist\",\"notes\":\"Notesome\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/citizen/appointment")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toEdit)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @WithUserDetails("cit1")
    public void stage3_deleteAppointmentById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/citizen/appointment/"+ testingAppointmentId)
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("cit1")
    public void getAppointmentById() throws Exception {
        String expected = "{\"appointmentId\":3,\"date\":\"2018-12-05\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}}";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/citizen/appointment/3")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @WithUserDetails("cit1")
    public void getSpecialties() throws Exception {
        String expected = "[{\"specialtyId\":1,\"specialty\":\"Cardiologist\"},{\"specialtyId\":2,\"specialty\":\"Neurologist\"},{\"specialtyId\":3,\"specialty\":\"Dentist\"},{\"specialtyId\":4,\"specialty\":\"Surgent\"},{\"specialtyId\":5,\"specialty\":\"Pediatrician\"},{\"specialtyId\":6,\"specialty\":\"Ophthalmologist\"},{\"specialtyId\":7,\"specialty\":\"Dermatology\"},{\"specialtyId\":8,\"specialty\":\"Anesthesiology\"},{\"specialtyId\":9,\"specialty\":\"Otolaryngologist\"},{\"specialtyId\":10,\"specialty\":\"Oncologist\"}]";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/citizen/specialties")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @WithUserDetails("cit1")
    public void getDoctorsBySpecialtyId() throws Exception {
        String expected = "[{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},{\"doctorId\":11,\"firstname\":\"Dimitris\",\"lastname\":\"Apostolou\",\"phone\":\"6971233699\",\"user\":{\"userId\":11,\"username\":\"doc11\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}}]";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/citizen/doctor/1")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }

    @Test
    @WithUserDetails("cit1")
    public void getCitAppointments() throws Exception {
        String expected = "[{\"appointmentId\":1,\"date\":\"2018-12-03\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":3,\"date\":\"2018-12-05\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":5,\"date\":\"2018-12-07\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":7,\"date\":\"2018-12-09\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":9,\"date\":\"2018-12-11\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":11,\"date\":\"2018-12-13\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":13,\"date\":\"2018-12-15\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":15,\"date\":\"2018-12-17\",\"time\":\"18:00\",\"illnessHistory\":\"Eimai ypertasikos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":17,\"date\":\"2018-12-19\",\"time\":\"13:30\",\"illnessHistory\":\"Vimatodotis\",\"notes\":\"\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}},{\"appointmentId\":19,\"date\":\"2018-12-21\",\"time\":\"12:30\",\"illnessHistory\":\"Miniskos\",\"notes\":\"pairnw xapia\",\"doctor\":{\"doctorId\":1,\"firstname\":\"Kostas\",\"lastname\":\"Petrou\",\"phone\":\"6971233698\",\"user\":{\"userId\":1,\"username\":\"doc1\",\"role\":\"DOCTOR\"},\"specialty\":{\"specialtyId\":1,\"specialty\":\"Cardiologist\"}},\"citizen\":{\"citizenId\":1,\"firstname\":\"Vaggelis\",\"lastname\":\"Giazitzidis\",\"email\":\"vaggelisgiazitz@gmail.com\",\"phone\":\"6978986578\",\"ssn\":\"12345678912\",\"user\":{\"userId\":21,\"username\":\"cit1\",\"role\":\"CITIZEN\"}}}]";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/citizen/appointments")
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(content().string(expected));
    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}