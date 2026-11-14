/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import config.koneksi;
import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class modelPengguna {

    private String idPengguna, namaPengguna,username, password, role;

    public modelPengguna() {
    }

    public modelPengguna(String idPengguna, String namaPengguna, String username, String password, String role) {
        this.idPengguna = idPengguna;
        this.namaPengguna = namaPengguna;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setIdPengguna() {
        this.idPengguna = UUID.randomUUID().toString();
    }
    
    public void setIdPengguna(String idPengguna) {
        this.idPengguna = idPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public String getNamaPengguna() {
        return namaPengguna;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    
    public boolean tambah() {
        setIdPengguna();
        String sql = "INSERT INTO pengguna "
                + "(id_pengguna, nama_pengguna, password, role)"
                + "VALUES (?,?,?, MD5?,?)";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idPengguna);
            ps.setString(2, namaPengguna);
            ps.setString(3, username);
            ps.setString(4, password);
            ps.setString(5, role);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean ubah() {
        String sql;
        if(password != null || password.isEmpty()){   //jika password kosong
            sql = "UPDATE pengguna SET "
                + "nama_pengguna = ?, username = ?, role = ? "
                + "WHERE id_pengguna = ?";
        }else{
            sql = "UPDATE pengguna SET "       //jika password sudah terisi
                + "nama_pengguna = ?, username = ?, password = md5(?), role = ? "
                + "WHERE id_pengguna = ?";
        }
  
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, namaPengguna);
                ps.setString(2, username);
            if (password != null || password.isEmpty()) {   //jika password kosong
                ps.setString(3, role);
                ps.setString(4, idPengguna);
            } else {    //jika password terisi
                ps.setString(3, password);
                ps.setString(4, role);
                ps.setString(5, idPengguna);
            }
             
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean hapus() {
        String sql = "DELETE FROM pengguna WHERE id_pengguna = ?";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idPengguna);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<modelPengguna>tampilSemua() {
          List<modelPengguna> daftar = new ArrayList<>();
          
          String sql = "SELECT * FROM pengguna ORDER BY nama_pengguna ASC";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                modelPengguna pengguna = new modelPengguna(
                        rs.getString("id_pengguna"),
                        rs.getString("nama_pengguna"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                
                daftar.add(pengguna);
            }
            
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan pengguna: " + e.getMessage());
        }
        return daftar;
    }

}
