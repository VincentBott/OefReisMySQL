package org.beta.vzw.db;


import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class App
{
    private static final String SELECT = "SELECT bestemming, startdatum, einddatum FROM reis";


    public static void main( String[] args ) throws SQLException {

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);

        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/testdb?useSSL=false&serverTimezone=Europe/Brussels", "root", "VDAB")){

            try(Statement select = conn.createStatement();

                ResultSet rs = select.executeQuery(SELECT)) {

                while (rs.next()) {

                    String bestemming = rs.getString("bestemming");

                    LocalDate startdatum = rs.getDate("startdatum").toLocalDate();
                    LocalDate einddatum= rs.getDate("einddatum").toLocalDate();

                    int periode = Period.between(startdatum, einddatum).getDays();

                    System.out.printf("Je gaat naar %s van %s tot %s: %d dagen%n", bestemming, startdatum.format(formatter),
                            einddatum.format(formatter), periode);

                }

            }
        }
    }
}
