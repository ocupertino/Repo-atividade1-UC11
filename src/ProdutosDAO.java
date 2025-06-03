/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement st;
    ResultSet rs;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public int salvar(ProdutosDTO produtos) {
        int status;
        try {
            st = (PreparedStatement) conn.prepareStatement("insert into produtos values (?,?,?,?)");

            st.setString(1, produtos.getId());
            st.setString(2, produtos.getNome());
            st.setString(3, produtos.getValor());
            st.setString(4, produtos.getStatus());

            status = st.executeUpdate();
            return status;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return e.getErrorCode();
        }
    }

    public boolean connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attuc11", "root", "1234");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }
    }

    public ProdutosDTO listar() {
        try {
            ProdutosDTO produtosDTO = new ProdutosDTO();
            st = conn.prepareCall("select * from produtos");
            rs = st.executeQuery();

            if (rs.next()) {
                produtosDTO.setId(rs.getString("id"));
                produtosDTO.setNome(rs.getString("nome"));
                produtosDTO.setValor(rs.getString("valor"));
                produtosDTO.setStatus(rs.getString("statuss"));
                return produtosDTO;
            } else {
                return null;
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
    public List<ProdutosDTO> listarStatus(String sta){
        String sql = "select * from produtos where statuss = ?";
        
        if (conn == null) {
            connectDB();
        }
        
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            
            stmt.setString(1, sta);
            
            ResultSet rs = stmt.executeQuery();
            
            List<ProdutosDTO> listaProdutos = new ArrayList();
            
            while (rs.next()) {                
                ProdutosDTO produtos = new ProdutosDTO();
                
                produtos.setId(rs.getString("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getString("valor"));
                produtos.setStatus(rs.getString("statuss"));
                
                listaProdutos.add(produtos);
            }
            return listaProdutos;
        } catch (SQLException exception) {
            return null;
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        return listagem;
    }

}
