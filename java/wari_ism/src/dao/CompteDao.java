/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Compte;
/**
 *
 * @author skabo
 */
public class CompteDao implements ISystem<Compte>{
    
   private DaoMysql dao;
   private final String SQL_INSERT="INSERT INTO `partenaire` (`mail`, `adresse`, `telephone`) VALUES (NULL, ?, ?, ?)";
   private final  String SQL_UPDATE="UPDATE `partenaire` SET `adresse`, `telephone` = ?, ?, ? WHERE `partenaire`.`mail` =?";         
   private final String SQL_SELECT_ONE="select * from partenaire where email=?";
   private final String SQL_SELECT_ALL="select * from partenaire";
 

 @Override
    public int create(Compte obj) {
        int result=0;
      
        try {
            dao.initPS(SQL_INSERT);
            dao.getPstm().setInt(1,obj.getProfil().getId() );
            dao.getPstm().setInt(2,0);
            dao.getPstm().setInt(3, obj.getNumero());
            dao.getPstm().setInt(4, obj.getSolde());
            dao.getPstm().setString(6, obj.partenaire());
            result=dao.executeMaj();
            
            dao.CloseConnection();
             
        } catch (SQLException ex) {
            Logger.getLogger(CompteDao.class.getNumero()).log(Level.SEVERE, null, ex);
        }
       
       return result;
    }

    @Override
    public int update(Compte obj) {
        int result=0;
      
        try {
            dao.initPS(SQL_UPDATE);
            dao.getPstm().setString(1, obj.getEtat());
            dao.getPstm().setInt(2, obj.getId());
            result=dao.executeMaj();
            
            dao.CloseConnection();
             
        } catch (SQLException ex) {
            Logger.getLogger(CompteDao.class.getNumero()).log(Level.SEVERE, null, ex);
        }
       
       return result;
    }

    @Override
    public Compte findById(int id) {
        User result=null;
     try {
         dao.initPS(SQL_SELECT_ONE);
         dao.getPstm().setInt(1, id);
          ResultSet rs=dao.executeSelect();
          if(rs.first()){
            result=new Compte();
            result.setId(rs.getInt("id_compte"));
            result.setNumero(rs.getInt("numero"));
            result.setSolde(rs.getInt("solde"));
            result.setPartenaire(rs.getString("partenaire"));

            int id_profil=rs.getInt("id_profil");
             Profil profil=new ProfilDao().findById(id_profil);
             result.setProfil(profil);
             
          }  
          
     } catch (SQLException ex) {
         Logger.getLogger(CompteDao.class.getNumero()).log(Level.SEVERE, null, ex);
     }
       
       return result; 
    } 

    @Override
    public List<Compte> findAll() {
        List<Compte> result=null;
        ProfilDao profildao=new ProfilDao();
        String sql="select * from compte";
     try {
         result=new ArrayList<>();
         dao.initPS(sql);
        ResultSet rs= dao.executeSelect();
        while(rs.next()){
            User u =new Compte();
            u.setId(rs.getInt("id_compte"));
            u.setNumero(rs.getInt("numero"));
            u.setSolde(rs.getInt("solde"));

            Profil p=profildao.findById(rs.getInt("id_profil"));
            u.setProfil(p);
            result.add(u);
        }
     } catch (SQLException ex) {
         Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
     }
        return result;
    }

}

