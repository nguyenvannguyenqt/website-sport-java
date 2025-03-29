package com.nhom07.DAMH_LTUD.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tournament_category")
public class TournamentCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Thể loại giải đấu không được để trống")
    @Size(min = 30,message = "Thể loại giải đấu phải có ít nhất 30 ký tự")
    @Column(name = "name_category_tournament")
    private String nameCategoryTournament;

    @OneToMany(mappedBy = "category_tournament")
    private List<Tournament> tournaments;
}
