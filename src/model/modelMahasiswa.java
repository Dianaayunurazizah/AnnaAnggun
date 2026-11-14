/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import config.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Acer
 */
public class modelMahasiswa {
    private String nim, nama;

    public modelMahasiswa() {
    }

    public modelMahasiswa(String nim, String nama) {
        this.nim = nim;
        this.nama = nama;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }
    
    public boolean tambah() {
        String sql = "INSERT INTO mahasiswa "
                + "(nim, nama) "
                + "VALUES (?,?)";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, nim);
            ps.setString(2, nama);

            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean ubah() {
        String sql = "UPDATE mahasiswa SET "
                + "nama = ? WHERE nim = ?";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, nama);
            ps.setString(2, nim);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean hapus() {
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, nim);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<modelMahasiswa>tampilSemua() {
          List<modelMahasiswa> daftar = new ArrayList<>();
          
          String sql = "SELECT * FROM mahasiswa ORDER BY nama ASC";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                modelMahasiswa mhs = new modelMahasiswa(
                        rs.getString("nim"),
                        rs.getString("nama")
                );
                
                daftar.add(mhs);
            }
            
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan mahasiswa: " + e.getMessage());
        }
        return daftar;
    }
}
