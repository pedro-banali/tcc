package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Desenvolvedor;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.TipoUsuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;

public class DesenvolvedorDAO {

	public List<Usuario> listarDev(Usuario usuario) {

		Connection cf = ConnectionFactory.getConnection();
		Usuario u;
		TipoUsuario tu;
		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "SELECT U.CPF, U.NOME, U.Nascimento, U.Login, UT.DATA_CADASTRO, UT.FK_TIPO_USUARIO, TU.DESCRICAO"
					+ " FROM USUARIO as U, USUARIO_TIPO as UT, TIPO_USUARIO as TU" + " WHERE U.CPF = UT.FK_CPF "
					+ " AND TU.ID_TIPO_USUARIO = UT.FK_TIPO_USUARIO " + " AND U.Gerente = ?";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

				preparedStatement.setLong(1, usuario.getCpf());

				// preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {

					u = new Usuario();
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
	
	public Desenvolvedor selectDev(Usuario usuario) {

		Connection cf = ConnectionFactory.getConnection();
		Desenvolvedor u = null;
		TipoUsuario tu;
		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "SELECT U.CPF, U.NOME, U.Nascimento, U.Login, UT.DATA_CADASTRO, UT.FK_TIPO_USUARIO, TU.DESCRICAO"
					+ " FROM USUARIO as U, USUARIO_TIPO as UT, TIPO_USUARIO as TU" + " WHERE U.CPF = UT.FK_CPF "
					+ " AND TU.ID_TIPO_USUARIO = UT.FK_TIPO_USUARIO " + " AND U.CPF = ?";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

				preparedStatement.setLong(1, usuario.getCpf());

				// preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {

					u = new Desenvolvedor();
					tu = new TipoUsuario();

					u.setNome(rs.getString("Nome"));
					u.setCpf(rs.getLong("Cpf"));
					u.setDataNascimento(rs.getDate("Nascimento"));

					tu.setId(rs.getInt("FK_TIPO_USUARIO"));
					tu.setDescricaoUsuario(rs.getString("DESCRICAO"));

					u.setTipoUsuario(tu);
					u.setDataCadastro(rs.getDate("DATA_CADASTRO"));


				

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
	
	public Usuario selectDevByLogin(Usuario usuario) {

		Connection cf = ConnectionFactory.getConnection();
		Usuario u = null;
		TipoUsuario tu;
		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "SELECT U.CPF, U.NOME, U.Nascimento, U.Login, UT.DATA_CADASTRO, UT.FK_TIPO_USUARIO, TU.DESCRICAO"
					+ " FROM USUARIO as U, USUARIO_TIPO as UT, TIPO_USUARIO as TU" + " WHERE U.CPF = UT.FK_CPF "
					+ " AND TU.ID_TIPO_USUARIO = UT.FK_TIPO_USUARIO " + " AND U.Login = ?";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

				preparedStatement.setString(1, usuario.getLogin());

				// preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {

					u = new Usuario();
					tu = new TipoUsuario();

					u.setNome(rs.getString("Nome"));
					u.setCpf(rs.getLong("Cpf"));
					u.setDataNascimento(rs.getDate("Nascimento"));

					tu.setId(rs.getInt("FK_TIPO_USUARIO"));
					tu.setDescricaoUsuario(rs.getString("DESCRICAO"));

					u.setTipoUsuario(tu);
					u.setDataCadastro(rs.getDate("DATA_CADASTRO"));


				

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

	public boolean cadastrarDev(Desenvolvedor u) {
		// TODO Auto-generated method stub

		Connection cf = ConnectionFactory.getConnection();

		java.sql.Date dataSql = new java.sql.Date(u.getDataNascimento().getTime());
		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "INSERT INTO USUARIO " + " (CPF, Nome, Nascimento, Senha, Login, Gerente, Ativo) "
					+ " VALUES ( ?, ?, ?, ?, ?, ?, 0)";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

				preparedStatement.setLong(1, u.getCpf());
				preparedStatement.setString(2, u.getNome());
				preparedStatement.setDate(3, dataSql);
				preparedStatement.setString(4, u.getSenha());
				preparedStatement.setString(5, u.getLogin());
				preparedStatement.setLong(6, u.getGerente().getCpf());

				// preparedStatement.setString(1, "Pedro Henrique Banali");
				int rs = preparedStatement.executeUpdate();
				cf.close();
				if (rs > 0)
					return true;
				else
					return false;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean editarDev(Desenvolvedor u, long cpf) {
		// TODO Auto-generated method stub

		Connection cf = ConnectionFactory.getConnection();

		java.sql.Date dataSql = new java.sql.Date(u.getDataNascimento().getTime());
		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "UPDATE USUARIO SET CPF = ? , Nome = ?, Nascimento = ?, Login = ? WHERE CPF=?";
			
			PreparedStatement preparedStatement;
			
			try {
				preparedStatement = cf.prepareStatement(selectSQL);
				
				cf.setAutoCommit(false);
				

				preparedStatement.setLong(1, u.getCpf());
				preparedStatement.setString(2, u.getNome());
				preparedStatement.setDate(3, dataSql);
				
				preparedStatement.setString(4, u.getLogin());
				preparedStatement.setLong(5, cpf);

				// preparedStatement.setString(1, "Pedro Henrique Banali");

				int rs = preparedStatement.executeUpdate();
				cf.commit();
				cf.close();
				if (rs > 0)
					return true;
				else
					return false;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean excluirDev(Desenvolvedor u) {
		// TODO Auto-generated method stub

		Connection cf = ConnectionFactory.getConnection();

		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "UPDATE USUARIO SET Ativo= 1 WHERE CPF=?";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

				preparedStatement.setLong(1, u.getCpf());

				// preparedStatement.setString(1, "Pedro Henrique Banali");
				int rs = preparedStatement.executeUpdate();
				cf.close();
				if (rs > 0)
					return true;
				else
					return false;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}
