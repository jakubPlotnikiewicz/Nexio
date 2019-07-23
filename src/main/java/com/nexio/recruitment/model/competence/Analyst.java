package com.nexio.recruitment.model.competence;

import com.nexio.recruitment.model.competence.Interface.Competences;

public class Analyst implements Competences {
    @Override
    public String getCompetences() {
        return "Low";
    }
}
