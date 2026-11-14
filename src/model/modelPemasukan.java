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
import java.util.UUID;

/**
 *
 * @author Acer
 */
public class modelPemasukan {

    private String idPemasukan, tanggal,keterangan, idPengguna;
    private Integer nominal;

    public modelPemasukan() {
    }

    public modelPemasukan(String idPemasukan, String tanggal, Integer nominal, String keterangan, String idPengguna) {
        this.idPemasukan = idPemasukan;
        this.tanggal = tanggal;
        this.nominal = nominal;
        this.keterangan = keterangan;
        this.idPengguna = idPengguna;
    }

    public void setIdPemasukan() {
        this.idPemasukan = UUID.randomUUID().toString();
    }
    
    public void setIdPemasukan(String idPemasukan) {
        this.idPemasukan = idPemasukan;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
    
    public void setKeterangan(String keterangan){
        this.keterangan = keterangan;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public void setIdPengguna(String idPengguna) {
        this.idPengguna = idPengguna;
    }

    public String getIdPemasukan() {
        return idPemasukan;
    }

    public String getTanggal() {
        return tanggal;
    }
    
    public String getKeterangan() {
        return keterangan;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public Integer getNominal() {
        return nominal;
    }
    
    public boolean tambah() {
        setIdPemasukan();
        String sql = "INSERT INTO pemasukan "
                + "(id_pemasukan, tanggal, nominal, keterangan, id_pengguna)"
                + "VALUES (?,?,?,?,?)";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idPemasukan);
            ps.setString(2, tanggal);
            ps.setInt(3, nominal);
            ps.setString(4, keterangan);
            ps.setString(5, idPengguna);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean ubah() {
        String sql = "UPDATE pemasukan SET "
                + "tanggal = ?, nominal = ?, keterangan = ?, id_pengguna = ? "
                + "WHERE id_pemasukan = ?";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, tanggal);
            ps.setInt(2, nominal);
            ps.setString(3, keterangan);
            ps.setString(4, idPengguna);
            ps.setString(5, idPemasukan);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean hapus() {
        String sql = "DELETE FROM pemasukan WHERE id_pemasukan = ?";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idPemasukan);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<modelPemasukan>tampilSemua() {
          List<modelPemasukan> daftar = new ArrayList<>();
          
          String sql = "SELECT k.id_pemasukan, k.tanggal, k.nominal, k.keterangan, g.nama_pengguna FROM pemasukan k JOIN pengguna g "
                  +"ON k.id_Pengguna = g.id_pengguna "
                  + "ORDER BY k.tanggal DESC";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                modelPemasukan pemasukan = new modelPemasukan(
                        rs.getString("id_pemasukan"),
                        rs.getString("tanggal"),
                        rs.getInt("nominal"),
                        rs.getString("keterangan"),
                        rs.getString("nama_pengguna")
                );
                
                daftar.add(pemasukan);
            }
            
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan pemasukan: " + e.getMessage());
        }
        return daftar;
    }

}
