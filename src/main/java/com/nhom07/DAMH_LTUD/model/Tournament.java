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
@Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên giải đấu không được để trống")
    @Size(min = 50,message = "Tên giải đấu phải có ít nhất 50 ký tự")
    @Column(name = "name_tournament")
    private String name;

    @OneToMany(mappedBy = "tournament")
    private List<Region> regions;

    @OneToMany(mappedBy = "tournament")
    private List<LeagueClub> leagueClubs;


    @ManyToOne
    @JoinColumn(name = "tournament_category_id")
    private TournamentCategory category_tournament;

}
