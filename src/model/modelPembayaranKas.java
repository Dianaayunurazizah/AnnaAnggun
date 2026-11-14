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
public class modelPembayaranKas {

    private String idPembayaran, tanggal, status, idPengguna, nim;
    private Integer jumlahBayar;

    public modelPembayaranKas() {
    }

    public modelPembayaranKas(String idPembayaran, String tanggal, Integer jumlahBayar, String status, String idPengguna, String nim) {
        this.idPembayaran = idPembayaran;
        this.tanggal = tanggal;
        this.jumlahBayar = jumlahBayar;
        this.status = status;
        this.idPengguna = idPengguna;
        this.nim = nim;
    }

    public void setIdPembayaran() {
        this.idPembayaran = UUID.randomUUID().toString();
    }
    
    public void setIdPembayaran(String idPembayaran) {
        this.idPembayaran = idPembayaran;
    }


    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setIdPengguna(String idPengguna) {
        this.idPengguna = idPengguna;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setJumlahBayar(Integer jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public String getNim() {
        return nim;
    }

    public Integer getJumlahBayar() {
        return jumlahBayar;
    }
    
    public boolean tambah() {
        setIdPembayaran();
        String sql = "INSERT INTO pembayaran_kas "
                + "(id_pembayaran, tanggal, jumlah_bayar, status, id_pengguna , nim)"
                + "VALUES (?,?,?,?,?,?)";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idPembayaran);
            ps.setString(2, tanggal);
            ps.setInt(3, jumlahBayar);
            ps.setString(4, status);
            ps.setString(5, idPengguna);
            ps.setString(6, nim);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean ubah() {
        String sql = "UPDATE pembayaran_kas SET "
                + "tanggal = ?, jumlah_bayar = ?,status = ?, id_pengguna = ?, nim = ? "
                + "WHERE id_pembayaran = ?";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, tanggal);
            ps.setInt(2, jumlahBayar);
            ps.setString(3, status);
            ps.setString(4, idPengguna);
            ps.setString(5, nim);
            ps.setString(6, idPembayaran);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean hapus() {
        String sql = "DELETE FROM pembayaran_kas WHERE id_pembayaran = ?";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idPembayaran);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<modelPembayaranKas>tampilSemua() {
          List<modelPembayaranKas> daftar = new ArrayList<>();
          
          String sql = "SELECT k.id_pembayaran, k.tanggal, k.jumlah_bayar, k.status, g.nama_pengguna, m.nama "
                  +"FROM pembayaran_kas k JOIN pengguna g ON k.id_Pengguna = g.id_pengguna "
                  +"JOIN mahasiswa m ON k.nim = m.nim "
                  + "ORDER BY k.tanggal DESC";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                modelPembayaranKas pembayaran = new modelPembayaranKas(
                        rs.getString("id_pembayaran"),
                        rs.getString("tanggal"),
                        rs.getInt("jumlah_bayar"),
                        rs.getString("status"),
                        rs.getString("nama_pengguna"),
                        rs.getString("nama")
                );
                
                daftar.add(pembayaran);
            }
            
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan pembayaran kas: " + e.getMessage());
        }
        return daftar;
    }

}
