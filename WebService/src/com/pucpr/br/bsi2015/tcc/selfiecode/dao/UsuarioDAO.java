package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.TipoUsuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class UsuarioDAO {
	public Usuario login(Usuario usuario) {
		
		

		Connection cf = ConnectionFactory.getConnection();
		Usuario u =  new Usuario();
		TipoUsuario tu = new TipoUsuario();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "SELECT U.CPF, U.NOME, U.Nascimento, U.Login, UT.DATA_CADASTRO, UT.FK_TIPO_USUARIO, TU.DESCRICAO"
					+ " FROM USUARIO as U, USUARIO_TIPO as UT, TIPO_USUARIO as TU"
					+ " WHERE U.CPF = UT.FK_CPF "
					+ " AND TU.ID_TIPO_USUARIO = UT.FK_TIPO_USUARIO "
					+ " AND U.Login = ? AND SENHA = ?";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
 
				preparedStatement.setString(1, usuario.getLogin());
				preparedStatement.setString(2, usuario.getSenha());
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
		
					u.setNome(rs.getString("Nome"));
					u.setCpf(rs.getLong("Cpf"));
					u.setDataNascimento(rs.getDate("Nascimento"));
					
					tu.setId(rs.getInt("FK_TIPO_USUARIO"));
					tu.setDescricaoUsuario(rs.getString("DESCRICAO"));
					
					u.setTipoUsuario(tu);
					u.setDataCadastro(rs.getDate("DATA_CADASTRO"));
					preparedStatement = cf.prepareStatement(selectSQL);					
									
				}
				
				cf.close();
				return u;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
public List<Usuario> listarDev(Usuario usuario) {
		
		

		Connection cf = ConnectionFactory.getConnection();
		Usuario u;
		TipoUsuario tu;
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		if(cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else
		{
			String selectSQL = "SELECT U.CPF, U.NOME, U.Nascimento, U.Login, UT.DATA_CADASTRO, UT.FK_TIPO_USUARIO, TU.DESCRICAO"
					+ " FROM USUARIO as U, USUARIO_TIPO as UT, TIPO_USUARIO as TU"
					+ " WHERE U.CPF = UT.FK_CPF "
					+ " AND TU.ID_TIPO_USUARIO = UT.FK_TIPO_USUARIO "
					+ " AND U.Gerente = ?";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
 
				preparedStatement.setLong(1, usuario.getCpf());
				
				//preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {
					
					u =  new Usuario();
					tu = new TipoUsuario();
					
					u.setNome(rs.getString("Nome"));
					u.setCpf(rs.getLong("Cpf"));
					u.setDataNascimento(rs.getDate("Nascimento"));
					
					tu.setId(rs.getInt("FK_TIPO_USUARIO"));
					tu.setDescricaoUsuario(rs.getString("DESCRICAO"));
					
					u.setTipoUsuario(tu);
					u.setDataCadastro(rs.getDate("DATA_CADASTRO"));
					preparedStatement = cf.prepareStatement(selectSQL);
					
					usuarios.add(u);
					
									
				}
				cf.close();
				return usuarios;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

public boolean cadastrarDev(Usuario u) {
	// TODO Auto-generated method stub
	
	return false;
}
}
