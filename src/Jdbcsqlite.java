import java.sql.*;
import java.util.Scanner;

public class Main {
    // text color
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31;1m";
    public static final String ANSI_GREEN = "\u001B[32;1m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String KURV = "\u001B[3m";
    public static final String BOLD = "\u001B[1m";
    public static final String Back_LithGrow = "\u001B[107m";

    private static Connection connection() throws SQLException {
        Connection connection = null;
        String url = "jdbc:sqlite:/C:/sqlite/db/football.db";
        connection = DriverManager.getConnection(url);
        return connection;
    }

    private static void selectFootballer() throws SQLException {
        String sqlF = "SELECT * FROM footballer";
        Connection connection=connection();
        Statement stmtF = connection.createStatement();
        ResultSet rsF = stmtF.executeQuery(sqlF);
        System.out.println();
        System.out.println(ANSI_BLUE + KURV + BOLD + "                                     [Footballer table]" + ANSI_RESET);
        System.out.format(ANSI_GREEN + "%-15s%-15s%-15s%-15s%-20s%-20s%-19s%n",
                "Name", "LastName", "DateOfBirth", "Nationality", "Position", "NumberOfGoals", "Id" + ANSI_RESET);
        System.out.println("------------------------------------------------" +
                "------------------------------------------------------");
        while (rsF.next()) {
            System.out.format(ANSI_RED + Back_LithGrow + "%-15s%-15s%-15s%-15s%-20s%-20s%-20s%n",
                    rsF.getString("name"),
                    rsF.getString("lastName"),
                    rsF.getInt("dateOfBirth"),
                    rsF.getString("nationality"),
                    rsF.getString("position"),
                    rsF.getInt("numberOfGoals"),
                    rsF.getInt("footballerId") + ANSI_RESET);
        }
        connection.close();

    }

    private static void selectTeam() throws SQLException {
        String sqlT = "SELECT * FROM team";
        Connection connection=connection();
        Statement stmtT = connection.createStatement();
        ResultSet rsT = stmtT.executeQuery(sqlT);
        System.out.println();
        System.out.println(ANSI_BLUE + KURV + BOLD + "                                                     [Team table]" + ANSI_RESET);
        System.out.format(ANSI_GREEN + "%-25s%-30s%-30s%-20s%-19s%-10s%n",
                "TeamName", "League", "HomeArena", "Coach", "Country", "Id" + ANSI_RESET);
        System.out.println("-------------------------------------------------------------" +
                "-----------------------------------------------------------------");
        while (rsT.next()) {
            System.out.format(ANSI_RED + Back_LithGrow + "%-25s%-30s%-30s%-20s%-20s%-20s%n",
                    rsT.getString("teamName"),
                    rsT.getString("league"),
                    rsT.getString("homeArena"),
                    rsT.getString("coach"),
                    rsT.getString("country"),
                    rsT.getInt("teamId") + ANSI_RESET);
        }
        connection.close();
    }
    private static void insertFootballer(String name, String lastName, int DateOfBirth, String Nationality
            , String Position, int NumberOfGoals, int teamId) throws SQLException {
        String sql = "INSERT INTO footballer(name,lastName,dateOfbirth,nationality,position,numberOfGoals,teamId)" +
                " values(?,?,?,?,?,?,?)";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, lastName);
        pstmt.setInt(3, DateOfBirth);
        pstmt.setString(4, Nationality);
        pstmt.setString(5, Position);
        pstmt.setInt(6, NumberOfGoals);
        pstmt.setInt(7, teamId);
        pstmt.executeUpdate();
        System.out.println(ANSI_BLUE + "A new football player has been added to the footballer table" + ANSI_RESET);
        connection.close();
    }

    private static void insertTeam(String name, String coach, String homeArena
            , String league, String country) throws SQLException {
        String sql = "INSERT INTO team(teamName,coach,homeArena,league,country) values(?,?,?,?,?)";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, coach);
        pstmt.setString(3, homeArena);
        pstmt.setString(4, league);
        pstmt.setString(5, country);
        pstmt.executeUpdate();
        System.out.println(ANSI_BLUE + "A new team has been added to the team table" + ANSI_RESET);
        connection.close();
    }

    private static void updateFootballer(int id, String name, String lastName, int DateOfBirth, String Nationality
            , String Position, int NumberOfGoals, int teamId) throws SQLException {
        String sql = "UPDATE footballer SET name=?,lastName=?,dateOfBirth=?,nationality=?,position=?," +
                "numberOfGoals=?,teamId=? WHERE footballerId=?";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, lastName);
        pstmt.setInt(3, DateOfBirth);
        pstmt.setString(4, Nationality);
        pstmt.setString(5, Position);
        pstmt.setInt(6, NumberOfGoals);
        pstmt.setInt(7, teamId);
        pstmt.setInt(8, id);
        pstmt.executeUpdate();
        System.out.println(ANSI_BLUE + "You have updated a football player with id: " + id + ANSI_RESET);
        connection.close();
    }

    private static void updateTeam(int id, String name, String coach, String homeArena
            , String league, String country) throws SQLException {
        String sql = "UPDATE team SET teamName=?,coach=?,homeArena=?,league=?,country=? WHERE teamId=?";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setString(2, coach);
        pstmt.setString(3, homeArena);
        pstmt.setString(4, league);
        pstmt.setString(5, country);
        pstmt.setInt(6, id);
        pstmt.executeUpdate();
        System.out.println(ANSI_BLUE + "You have updated a team with id: " + id + ANSI_RESET);
        connection.close();
    }

    private static void deleteFootballer(int id) throws SQLException {
        String sql = "DELETE FROM footballer WHERE footballerId=?";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        System.out.println(ANSI_BLUE + "You have removed a football player with ID: " + id + ANSI_RESET);
        connection.close();
    }

    private static void deleteTeam(int teamId) throws SQLException {
        String sql = "DELETE FROM team WHERE teamId=?";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, teamId);
        pstmt.executeUpdate();
        System.out.println(ANSI_BLUE + "You have removed a football player with ID: " + teamId + ANSI_RESET);
        connection.close();

    }

    private static void join() throws SQLException {
        String sql = "SELECT * FROM footballer JOIN team USING(teamId)";
        Connection connection=connection();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println();
        System.out.println(ANSI_BLUE + KURV + BOLD + "                                             [Koppling melan två tableter team och footballer]" + ANSI_RESET);
        System.out.format(ANSI_GREEN + "%-10s%-15s%-15s%-15s%-20s%-20s%-20s%-20s%-14s%-3s%n", "Name", "LastName", "Brith", "Nationality", "Position",
                "TeamName", "Coach", "HomeArena", "Country", "Id" + ANSI_RESET);
        System.out.println("------------------------------------------------------------------------------" +
                "--------------------------------------------------------------------------");
        while (rs.next()) {
            System.out.format(ANSI_RED + Back_LithGrow + "%-10s%-15s%-15s%-15s%-20s%-20s%-20s%-20s%-15s%-5s%n",
                    rs.getString("name"),
                    rs.getString("lastName"),
                    rs.getInt("dateOfBirth"),
                    rs.getString("nationality"),
                    rs.getString("position"),
                    rs.getString("teamName"),
                    rs.getString("coach"),
                    rs.getString("homeArena"),
                    rs.getString("country"),
                    rs.getInt("footballerId") + ANSI_RESET);
        }
        connection.close();
    }

    private static void searchFootballer(String name) throws SQLException {
        String sqlF = "SELECT * FROM footballer WHERE name=?";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sqlF);
        pstmt.setString(1, name);
        ResultSet rsF = pstmt.executeQuery();
        System.out.println();
        System.out.println(BOLD + KURV + ANSI_BLUE + "                                            [Resultat efter sökning]" + ANSI_RESET);
        System.out.format(ANSI_GREEN + "%-15s%-20s%-15s%-15s%-17s%-17s%-20s%n",
                "Name", "LastName", "DateOfBirth", "Nationality", "Position", "NumberOfGoals", "Id" + ANSI_RESET);
        System.out.println("----------------------------------------------" +
                "-------------------------------------------------------");
        while (rsF.next()) {
            System.out.format(ANSI_RED + Back_LithGrow + "%-15s%-20s%-15s%-15s%-17s%-17s%-20s%n",
                    rsF.getString("name"),
                    rsF.getString("lastName"),
                    rsF.getInt("dateOfBirth"),
                    rsF.getString("nationality"),
                    rsF.getString("position"),
                    rsF.getInt("numberOfGoals"),
                    rsF.getInt("footballerId") + ANSI_RESET);
        }
        connection.close();
    }

    private static void addFavorite(int id) throws SQLException {
        String sql = "UPDATE footballer SET favorite=1 WHERE footballerId=?";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        System.out.println(ANSI_BLUE + "You have added a football player with ID: " + id + " som favorite" + ANSI_RESET);
        connection.close();
    }

    private static void someFavorite() throws SQLException {
        String sqlF = "SELECT * FROM footballer WHERE favorite=1";
        Connection connection=connection();
        PreparedStatement stmtF = connection.prepareStatement(sqlF);
        ResultSet rsF = stmtF.executeQuery();
        System.out.println();
        System.out.println(BOLD + ANSI_BLUE + KURV + "                                   [Här kommer alla dina favorit fotbollspelare]" + ANSI_RESET);
        System.out.format(ANSI_GREEN + "%-5s%-15s%-15s%-15s%-20s%-20s%-20s%-20s%n",
                "Id", "Name", "LastName", "DateOfBirth", "Nationality", "Position", "NumberOfGoals", "Favorite" + ANSI_RESET);
        System.out.println("----------------------------------------------------------" +
                "-------------------------------------------------------------");
        while (rsF.next()) {
            System.out.format(ANSI_RED + Back_LithGrow + "%-5s%-15s%-15s%-15s%-20s%-20s%-25s%-20s%n",
                    rsF.getInt("footballerId"),
                    rsF.getString("name"),
                    rsF.getString("lastName"),
                    rsF.getInt("dateOfBirth"),
                    rsF.getString("nationality"),
                    rsF.getString("position"),
                    rsF.getInt("numberOfGoals"),
                    rsF.getInt("favorite") + ANSI_RESET);
        }
        connection.close();

    }

    private static void numberOfFootballers() throws SQLException {
        String sql = "SELECT COUNT(*) AS countF FROM footballer";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        System.out.println(ANSI_GREEN + "Antal footballer i tabelen är: " + rs.getInt("countF") + ANSI_RESET);
        connection.close();
    }

    private static void numberOfTeam() throws SQLException {
        String sql = "SELECT COUNT(*) AS countF FROM team";
        Connection connection=connection();
        PreparedStatement pstmt = connection.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        System.out.println(ANSI_GREEN + "Antal team i tabelen är: " + rs.getInt("countF") + ANSI_RESET);
        connection.close();
    }

    private static void menu() {
        String[] team = {
                " ",
                "                                    Please select an option!",
                " ",
                "\t1- Display the teams table",
                "\t2- Add a team to the table",
                "\t3- Update a team",
                "\t4- Delete a team",
                "\t5- Display the relations between tables",
                "\t6- Show the number of teams in the table",
                "\t7- Show all my favorites footballer",};
        String[] footballer = {
                " ",
                " ",
                " ",
                "8- Display the footballers table",
                "9- Add a footballer to the table",
                "10- Update a footballer",
                "11- Delete a footballer",
                "12- Search for a footballer",
                "13- Add a footballer as your favorite",
                ANSI_RED + "14- Exit" + ANSI_RESET};
        int j = 0;
        for (int i = 0; i < team.length; i++, j++) {
            System.out.format(ANSI_GREEN + BOLD + "%-60s%-70s%n", team[i], footballer[j] + ANSI_RESET);
        }
    }

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String select = null;
        do {
            menu();
            select = scanner.nextLine();
            switch (select) {
                case "1":
                    selectTeam();
                    break;
                case "2":
                    insertTeam("Manchester United", "Ole Gunnar Solskjær",
                            "Old Trafford", "Premier League", "England");
                    selectTeam();
                    break;
                case "3":
                    updateTeam(6, "Manchester United", "Mikel Arteta",
                            "New Trafford", "Premier League,UEFA", "England");
                    selectTeam();
                    break;
                case "4":
                    deleteTeam(6);
                    selectTeam();
                    break;
                case "5":
                    join();
                    break;
                case "6":
                    numberOfTeam();
                    break;
                case "7":
                    someFavorite();
                    break;
                case "8":
                    selectFootballer();
                    break;
                case "9":
                    insertFootballer("Cristiano","Ronaldo",1985,
                            "Portuguese","Forward",700,3);
                    selectFootballer();
                    break;
                case "10":
                    updateFootballer(6, "Cristiano", "Ronaldo", 1985,
                            "Portuguese", "Centre mifield", 850, 3);
                    selectFootballer();
                    break;
                case "11":
                    deleteFootballer(6);
                    selectFootballer();
                    break;
                case "12":
                    searchFootballer("Zlatan");
                    break;
                case "13":
                    addFavorite(4);
                    selectFootballer();
                    break;
                case "14":
                    System.out.println("The program ends");
                    break;
                default:
                    System.out.println("You kan write a number between 1 and 16");
            }
        }while (!select.equals("14"));

    }
}