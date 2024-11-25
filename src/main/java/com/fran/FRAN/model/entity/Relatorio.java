package com.fran.FRAN.model.entity;

import jakarta.persistence.*;
import lombok.*;

import com.fran.FRAN.model.entity.Status;

import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@Entity
@Table(name = "relatorio")
public class Relatorio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;
    private LocalTime horas_totais;
    private LocalTime horas_validas;
    private LocalDateTime data_entregue;

    @Enumerated(EnumType.STRING)
    private Mes mes_referencia;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "estagio_id", nullable = false)
    private Estagio estagio;

//    public static int calcularHorasMes(int cargaHorariaDiaria, Mes mes, int diasPorSemana) {
//        int anoAtual = LocalDate.now().getYear();
//        boolean anoBissexto = isAnoBissexto(anoAtual);
//        LocalDate primeiroDiaDoMes = LocalDate.of(anoAtual, mes.ordinal() + 1, 1);
//        LocalDate ultimoDiaDoMes = primeiroDiaDoMes.withDayOfMonth(mes.getDias(anoBissexto));
//        int diasUteis = 0;
//
//        for (LocalDate data = primeiroDiaDoMes; !data.isAfter(ultimoDiaDoMes); data = data.plusDays(1)) {
//            if (isDiaUtil(data, diasPorSemana)) {
//                diasUteis++;
//            }
//        }
//
//        return diasUteis * cargaHorariaDiaria;
//    }
//
//    private static boolean isAnoBissexto(int ano) {
//        return Year.isLeap(ano);
//    }

    //Metodo Aproximado, precisa de confirmação!!!!!!!!!!!!!!!!!!
//    private static boolean isDiaUtil(LocalDate data, int diasPorSemana) {
//        DayOfWeek diaDaSemana = data.getDayOfWeek();
//
//        switch (diasPorSemana) {
//            case 5: // Segunda a sexta-feira
//                return diaDaSemana != DayOfWeek.SATURDAY && diaDaSemana != DayOfWeek.SUNDAY;
//            case 4: // Segunda a quinta-feira
//                return diaDaSemana != DayOfWeek.FRIDAY && diaDaSemana != DayOfWeek.SATURDAY && diaDaSemana != DayOfWeek.SUNDAY;
//            case 3: // Segunda a quarta-feira
//                return diaDaSemana != DayOfWeek.THURSDAY && diaDaSemana != DayOfWeek.FRIDAY && diaDaSemana != DayOfWeek.SATURDAY && diaDaSemana != DayOfWeek.SUNDAY;
//            case 2: // Segunda e terça-feira
//                return diaDaSemana != DayOfWeek.WEDNESDAY && diaDaSemana != DayOfWeek.THURSDAY && diaDaSemana != DayOfWeek.FRIDAY && diaDaSemana != DayOfWeek.SATURDAY && diaDaSemana != DayOfWeek.SUNDAY;
//            case 1: // Só segunda-feira
//                return diaDaSemana == DayOfWeek.MONDAY;
//            default:
//                return false;
//        }
//    }
}