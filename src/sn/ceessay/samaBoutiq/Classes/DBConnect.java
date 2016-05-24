
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.Classes;

//~--- non-JDK imports --------------------------------------------------------
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import sn.ceessay.samaBoutiq.Classes.Carateristiques;
import sn.ceessay.samaBoutiq.Classes.LigneDeCommande;
import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.Model;
import sn.ceessay.samaBoutiq.FrontOffice_Boutiq.Notificateur;

//~--- JDK imports ------------------------------------------------------------
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author ceessay
 */
public class DBConnect {

    private final static String url = "jdbc:mysql://localhost:3306/samaboutiqbd";
    private final static String user = "root";
    private final static String pass = "mysql";
    public static Connection connection = null;
    private static Statement stmt = null;

    public static void ON() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(url, user, pass);
            stmt = connection.createStatement();
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erreur driver mysql nom trouvé\n" + ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur connection bd\n" + ex);
        }
    }

    public static ResultSet SELECT(String req) {
        try {
            if ((stmt == null) || (connection == null)) {
                ON();
            }

            return stmt.executeQuery(req);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Echec de recuperation des données\n" + ex);

//          } finally {
//
        }

        return null;
    }

    public static void INSERT(String req) {
        try {
            if ((stmt == null) || (connection == null)) {
                ON();
            }

            stmt.execute(req);
            JOptionPane.showMessageDialog(null, "Enregistrement effectué");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur Enregistrement\n" + ex.getMessage() + "\n" + ex.getCause());
        }
    }

    public static void UPDATE(String req) {
        try {
            if ((stmt == null) || (connection == null)) {
                ON();
            }

            stmt.executeUpdate(req);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur requeteUpdate");
        }
    }

    public static void OFF() {
        try {
            if ((stmt != null) || (connection != null)) {
                stmt.close();
                connection.close();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur de  férmeture de la connection");
        }
    }

    public static boolean inscrireClient(ArrayList<String> l) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Clients " + "("
                    + "nom, prenom, age, telephone, email, sexe, ville, region, adresse) "
                    + "VALUES( ?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            String prenom = l.get(1).substring(0, 1).toUpperCase() + l.get(1).substring(1).toLowerCase(Locale.FRENCH);

            ps.setString(1, l.get(0).toUpperCase(Locale.FRENCH));
            ps.setString(2, prenom);
            ps.setInt(3, Integer.valueOf(l.get(2)));
            ps.setString(4, l.get(3));
            ps.setString(5, l.get(4));
            ps.setString(6, l.get(5));
            ps.setString(7, l.get(6));
            ps.setString(8, l.get(7));
            ps.setString(9, l.get(8));

            ps.executeUpdate();

            ResultSet clesGeneres = ps.getGeneratedKeys();
            int idCli = -1;

            if (clesGeneres.next()) {
                idCli = clesGeneres.getInt(1);
            }

            boolean creerCompteclient = creerCompteclient(l.get(9), l.get(10), idCli);

            return creerCompteclient;
        } catch (SQLException ex) {
            System.out.println(ex.getCause() + "\n" + ex.getMessage());

            return false;
        }

//      finally {
//          try {
//              ps.close();
//          } catch (SQLException ex) {
//              Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
//          }
//      }
    }

    public static boolean creerCompteclient(String pseudo, String mdp, int idCli) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Comptes (pseudo, motdepasse, idClient)"
                    + "VALUES (?,?,?)");

            ps.setString(1, pseudo.toLowerCase(Locale.FRENCH));
            ps.setString(2, mdp);
            ps.setInt(3, idCli);

            int exec = ps.executeUpdate();

            return (exec > 0);
        } catch (MySQLIntegrityConstraintViolationException ex) {

            return false;
        } catch (SQLException ex) {
            System.out.println(ex.getCause() + "\n" + ex.getMessage());

            return false;
        }

//      finally {
//          try {
//              ps.close();
//          } catch (SQLException ex) {
//              Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
//          }
//      }
    }

    public static Carateristiques getCaracteristiques(Integer idArticle) {
        Carateristiques c = null;
        String r = "SELECT * FROM Caracteristiques where idArticles = " + idArticle;
        ResultSet rs = SELECT(r);

        try {
            if (rs.next()) {
                c = new Carateristiques(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4),
                        rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getBoolean(9),
                        rs.getBoolean(10), rs.getBoolean(11), rs.getString(12), rs.getBoolean(13),
                        rs.getString(14), idArticle);

//                Integer idCarac, String os, String marqueCPU, Double cadenceCPU, String archiCPU,
//            Integer tailleRam, Integer taillDD, String tailleEcran, boolean wifi, boolean bluetooth,
//            boolean webcam, boolean camera, String resolutionWeb, String resolutionCam,
//            Integer idArticle
                return c;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getCause() + "\n" + ex.getMessage());

            return c;
        }

        return c;
    }

    public static boolean enregistrerCommande() {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Commandes (valeurCmd, dateCmd, idClient)"
                    + "Values (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            Model.PANIER.setDateCmd();
            ps.setInt(1, Model.PANIER.getValeurCmd());
            ps.setDate(2, Model.PANIER.getDateCmd());
            ps.setInt(3, Model.CLIENT.getIdClient());

            int executeUpdate = ps.executeUpdate();
            int idcmd = -1;

            if (executeUpdate > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    idcmd = rs.getInt(1);

                    // ps.close();
                }
            } else {
                return false;
            }

            return enregistrerLignesDeCommande(idcmd);
        } catch (SQLException ex) {
            System.out.println(ex.getCause() + "\n" + ex.getMessage());

            return false;
        }

//      finally{
//          try {
//              ps.close();
//          } catch (SQLException ex) {
//              Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
//          }
//      }
    }

    private static boolean enregistrerLignesDeCommande(int idcmd) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Lignedecommades ("
                    + "valeurlignecmd,quantite, idCmd, idArticles)"
                    + "Values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            for (LigneDeCommande ldc : Model.PANIER.getListeLignesDeCommande()) {
                ps.setInt(1, ldc.getValeur());
                ps.setInt(2, ldc.getNombreUnites());
                ps.setInt(3, idcmd);
                ps.setInt(4, ldc.getArticle().getIdArticle());
                ps.execute();
            }

            ResultSet rs = ps.getGeneratedKeys();

            for (LigneDeCommande ldc : Model.PANIER.getListeLignesDeCommande()) {
                decrementeArticle(ldc.getArticle().getIdArticle(), ldc.getNombreUnites());
            }

            // updateArticle(rs);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getCause() + "\n" + ex.getMessage());

            return false;
        }

//      finally{
//          try {
//              ps.close();
//              connection.setAutoCommit(true);
//          } catch (SQLException ex) {
//              Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
//          }
//      }
    }

    public static boolean ajouterArticle(ArrayList<String> l, ArrayList<String> l2, File f) {
        try {
            PreparedStatement ps
                    = connection.prepareStatement(
                            "INSERT INTO Articles "
                            + "(designation,prix, marque, gamme, description, categorie, quantitedispo, quantiteajoutee, quantitevendue, image)"
                            + " VALUES (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, l.get(0));
            ps.setInt(2, Integer.valueOf(l.get(1)));
            ps.setString(3, l.get(2));
            ps.setString(4, l.get(3));
            ps.setString(5, l.get(4));
            ps.setString(6, l.get(5));
            ps.setInt(7, Integer.valueOf(l.get(6)));
            ps.setInt(8, Integer.valueOf(l.get(6)));
            ps.setInt(9, 0);
            ps.setBinaryStream(10, new FileInputStream(f), f.length());

            int executeUpdate = ps.executeUpdate();
            int idArticle = -1;

            if (executeUpdate > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    idArticle = rs.getInt(1);
                }
            } else {
                return false;
            }

            if (enregistrerCararcArticle(idArticle, l2)) {
                Notificateur.Info("Nouvel article ajouté avec succés");

                return true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getCause() + "\n" + ex.getMessage());
        } catch (NullPointerException ex) {
            System.out.println(ex.getCause() + "\n" + ex.getMessage());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    private static boolean enregistrerCararcArticle(int idArticle, ArrayList<String> l2) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Caracteristiques "
                    + "(os, marqueCPU, CadenceCPU, archiCPU, tailleRAM, tailleDD, tailleEcran, "
                    + "wifi, bluetooth, webcam, resolutionWeb, camera, resolutionCam, idArticles) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, l2.get(0));
            ps.setString(2, l2.get(1));
            ps.setDouble(3, Double.valueOf(l2.get(2)));
            ps.setString(4, l2.get(3));
            ps.setDouble(5, Double.valueOf(l2.get(4)));
            ps.setDouble(6, Double.valueOf(l2.get(5)));
            ps.setDouble(7, Double.valueOf(l2.get(6)));
            ps.setBoolean(8, Boolean.valueOf(l2.get(7)));
            ps.setBoolean(9, Boolean.valueOf(l2.get(8)));
            ps.setBoolean(10, Boolean.valueOf(l2.get(9)));
            ps.setDouble(11, Double.valueOf(l2.get(10)));
            ps.setBoolean(12, Boolean.valueOf(l2.get(11)));
            ps.setDouble(13, Double.valueOf(l2.get(12)));
            ps.setInt(14, idArticle);

            return ps.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getCause() + "\n" + ex.getMessage());

            return false;
        }
    }

    private static boolean modiferCararcArticle(int idArticle, ArrayList<String> l2) {
        String req
                = "UPDATE Caracteristiques "
                + "SET os = ?, marqueCPU = ?, CadenceCPU = ?, archiCPU = ?, tailleRAM = ?, tailleDD = ?, tailleEcran = ?, "
                + "wifi = ?, bluetooth = ?, webcam = ?, resolutionWeb = ?, camera = ?, resolutionCam = ? WHERE idArticles = ? ";

        try {
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, l2.get(0));
            ps.setString(2, l2.get(1));
            ps.setDouble(3, Double.valueOf(l2.get(2)));
            ps.setString(4, l2.get(3));
            ps.setDouble(5, Double.valueOf(l2.get(4)));
            ps.setDouble(6, Double.valueOf(l2.get(5)));
            ps.setDouble(7, Double.valueOf(l2.get(6)));
            ps.setBoolean(8, Boolean.valueOf(l2.get(7)));
            ps.setBoolean(9, Boolean.valueOf(l2.get(8)));
            ps.setBoolean(10, Boolean.valueOf(l2.get(9)));
            ps.setDouble(11, Double.valueOf(l2.get(10)));
            ps.setBoolean(12, Boolean.valueOf(l2.get(11)));
            ps.setDouble(13, Double.valueOf(l2.get(12)));
            ps.setInt(14, idArticle);

            return ps.executeUpdate() != 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
    }

    public static ResultSet commandesClient(int idclient) {
        String req
                = "select Articles.designation, Lignedecommades.quantite, Commandes.dateCmd, Commandes.valeurCmd from Articles,"
                + "Lignedecommades,  Commandes, Clients WHERE Commandes.idClient = " + idclient
                + " AND Lignedecommades.idCmd = Commandes.idCmd And Lignedecommades.idArticles = Articles.idArticles";

        return SELECT(req);
    }

    private static void decrementeArticle(int idArti, int nb) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Articles SET quantitedispo=quantitedispo-" + nb
                    + " WHERE idArticles = " + idArti);

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE Articles SET quantitevendue= quantitevendue+"
                    + nb + "  WHERE idArticles = " + idArti);

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean modifierArticle(int idarti, ArrayList<String> l, ArrayList<String> l2, File f) {
        String req
                = "UPDATE Articles SET designation = ?,prix = ? , marque= ?, gamme= ?, description= ?, categorie= ?, quantitedispo= ?, quantiteajoutee= ?, quantitevendue= ?, image= ? WHERE idArticles =? ";

        try {
            PreparedStatement ps = connection.prepareStatement(req);

            ps.setString(1, l.get(0));
            ps.setInt(2, Integer.valueOf(l.get(1)));
            ps.setString(3, l.get(2));
            ps.setString(4, l.get(3));
            ps.setString(5, l.get(4));
            ps.setString(6, l.get(5));
            ps.setInt(7, Integer.valueOf(l.get(6)));
            ps.setInt(8, Integer.valueOf(l.get(6)));
            ps.setInt(9, 0);
            ps.setBinaryStream(10, new FileInputStream(f), f.length());
            ps.setInt(11, idarti);

            int executeUpdate = ps.executeUpdate();

            if (modiferCararcArticle(idarti, l2)) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }

        return false;
    }

    public static boolean supprimerArticle(int id) {
        PreparedStatement ps;
        try {
            String req1 = "DELETE FROM samaboutiqbd.Caracteristiques WHERE idArticles = ?";
            ps = connection.prepareStatement(req1);
            ps.setInt(1, id);
            int execute = ps.executeUpdate();

            String req2 = "DELETE FROM samaboutiqbd.Articles WHERE idArticles = ?";
            ps = connection.prepareStatement(req2);
            ps.setInt(1, id);
            int execute1 = ps.executeUpdate();

            return execute != 0 && execute1 != 0;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Cet article ne doit pas être supprimé car il figure dans d'autres commandes."
                    + "\nSa suppression risque d'entrainer des erreurs lors des calculs sur les commandes");

        }

        return false;

    }

    public static boolean supprimerCmd(int id) {
        PreparedStatement ps;
        try {
            String req1 = "DELETE FROM samaboutiqbd.Lignedecommades WHERE idCmd = ?";
            ps = connection.prepareStatement(req1);
            ps.setInt(1, id);
            int execute = ps.executeUpdate();

            String req2 = "DELETE FROM samaboutiqbd.Commandes WHERE idCmd = ?";
            ps = connection.prepareStatement(req2);
            ps.setInt(1, id);
            int execute1 = ps.executeUpdate();

            return execute != 0 && execute1 != 0;

        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public static boolean changerEtatCmd(int idcmd, boolean selected) {

        PreparedStatement ps;

        String req = "UPDATE samaboutiqbd.Commandes SET `livree` = ? WHERE idCmd = ?";

        try {
            ps = connection.prepareStatement(req);
            ps.setBoolean(1, selected);
            ps.setInt(2, idcmd);

            return (ps.executeUpdate() != 0);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public static boolean supprimerClient(int i) throws SQLException {
        PreparedStatement ps = null;

        String req = "DELETE FROM Comptes WHERE idClient = ?";
        ps = connection.prepareStatement(req);
        ps.setInt(1, i);
        int executeUpdate = ps.executeUpdate();

        String req1 = "DELETE FROM Clients WHERE idClient = ?";
        ps = connection.prepareStatement(req1);
        ps.setInt(1, i);
        int executeUpdate1 = ps.executeUpdate();

        return (executeUpdate != 0 && executeUpdate1 != 0);

    }

    public static void supprimerCmdClient(int i) {

        PreparedStatement ps = null;
        try {

            String req = "SELECT idCmd FROM Commandes WHERE idClient = "+i;

            ResultSet rs = SELECT(req);

            while (rs.next()) {
                int id = rs.getInt(1);
                String req1 = "DELETE FROM samaboutiqbd.Lignedecommades WHERE idCmd = ?";
                ps = connection.prepareStatement(req1);
                ps.setInt(1, id);
                ps.executeUpdate();
            }

//            String req1 = "DELETE FROM samaboutiqbd.Lignedecommades WHERE idClient = ?";
//            ps = connection.prepareStatement(req1);
//            ps.setInt(1, i);
//            ps.executeUpdate();

            String req2 = "DELETE FROM samaboutiqbd.Commandes WHERE idClient = ?";
            ps = connection.prepareStatement(req2);
            ps.setInt(1, i);
            ps.executeUpdate();

            supprimerClient(i);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
