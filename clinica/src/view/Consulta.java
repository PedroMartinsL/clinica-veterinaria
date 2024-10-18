package view;

import java.time.LocalDateTime;

public class Consulta {
    private Pet pet;
    private String doenca;
    private LocalDateTime data;

    public Consulta(Pet pet, String doenca) {
        this.pet = pet;
        this.doenca = doenca;
        this.data = LocalDateTime.now(); // Define a data atual
    }

    // Getters e Setters
    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getDoenca() {
        return doenca;
    }

    public void setDoenca(String doenca) {
        this.doenca = doenca;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
