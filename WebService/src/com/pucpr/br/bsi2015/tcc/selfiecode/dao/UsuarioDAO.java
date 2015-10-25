package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Projeto;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.TipoUsuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Usuario;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.UsuarioProj;

public class UsuarioDAO {
	public Usuario login(Usuario usuario) {

		Connection cf = ConnectionFactory.getConnection();
		Usuario u = new Usuario();
		TipoUsuario tu = new TipoUsuario();

		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "SELECT U.CPF, U.NOME, U.Nascimento, U.Login, UT.DATA_CADASTRO, UT.FK_TIPO_USUARIO, TU.DESCRICAO"
					+ " FROM USUARIO as U, USUARIO_TIPO as UT, TIPO_USUARIO as TU" + " WHERE U.CPF = UT.FK_CPF "
					+ " AND TU.ID_TIPO_USUARIO = UT.FK_TIPO_USUARIO " + " AND U.Login = ? AND SENHA = ?";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

				preparedStatement.setString(1, usuario.getLogin());
				preparedStatement.setString(2, usuario.getSenha());
				// preparedStatement.setString(1, "Pedro Henrique Banali");
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

	public List<UsuarioProj> listarDevProj(Usuario usuario) {

		Connection cf = ConnectionFactory.getConnection();
		UsuarioProj u;
		;
		List<UsuarioProj> usuarios = new ArrayList<UsuarioProj>();

		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL;

			selectSQL = "SELECT U.Nome, P.NomeProjeto FROM USUARIO AS U" + "	, PROJETO AS P, USUARIO_PROJETO AS UP"
					+ "	WHERE UP.FK_USUARIO = U.CPF AND UP.FK_PROJETO = P.ID_PROJETO AND Gerente = ? "
					+ " AND U.Ativo = 0 ORDER BY U.Nome";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

				preparedStatement.setLong(1, usuario.getCpf());

				// preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {

					u = new UsuarioProj();

					u.setNome(rs.getString("Nome"));
					u.setNomeProjeto(rs.getString("NomeProjeto"));

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

	public List<Usuario> listarDev(Usuario usuario) {

		Connection cf = ConnectionFactory.getConnection();
		Usuario u;
		TipoUsuario tu;
		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL;
			if (usuario.getTipoUsuario().getId() == 3) {
				selectSQL = "SELECT U.CPF, U.NOME, U.Nascimento, U.Login,U.Senha, UT.DATA_CADASTRO, UT.FK_TIPO_USUARIO, TU.DESCRICAO"
						+ " FROM USUARIO as U, USUARIO_TIPO as UT, TIPO_USUARIO as TU" + " WHERE U.CPF = UT.FK_CPF "
						+ " AND TU.ID_TIPO_USUARIO = UT.FK_TIPO_USUARIO " + " AND U.CPF = ? "
						+ " AND UT.FK_TIPO_USUARIO = 3" + " AND U.Ativo = 0";
			} else {
				selectSQL = "SELECT U.CPF, U.NOME, U.Nascimento, U.Login,U.Senha, UT.DATA_CADASTRO, UT.FK_TIPO_USUARIO, TU.DESCRICAO"
						+ " FROM USUARIO as U, USUARIO_TIPO as UT, TIPO_USUARIO as TU" + " WHERE U.CPF = UT.FK_CPF "
						+ " AND TU.ID_TIPO_USUARIO = UT.FK_TIPO_USUARIO " + " AND U.Gerente = ? "
						+ " AND UT.FK_TIPO_USUARIO = 3" + " AND U.Ativo = 0";
			}
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
					u.setSenha(rs.getString("Senha"));
					u.setCpf(rs.getLong("Cpf"));
					u.setDataNascimento(rs.getDate("Nascimento"));
					u.setLogin(rs.getString("Login"));
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

	public List<Usuario> listarGer(Usuario usuario) {

		Connection cf = ConnectionFactory.getConnection();
		Usuario u;
		TipoUsuario tu;
		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL;

				selectSQL = "SELECT U.CPF, U.NOME, U.Nascimento, U.Login, U.Senha, UT.DATA_CADASTRO, UT.FK_TIPO_USUARIO, TU.DESCRICAO"
						+ " FROM USUARIO as U, USUARIO_TIPO as UT, TIPO_USUARIO as TU" + " WHERE U.CPF = UT.FK_CPF "
						+ " AND TU.ID_TIPO_USUARIO = UT.FK_TIPO_USUARIO "
						+ " AND UT.FK_TIPO_USUARIO = 2" + " AND U.Ativo = 0";
			
			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

//				preparedStatement.setLong(1, usuario.getCpf());

				// preparedStatement.setString(1, "Pedro Henrique Banali");
				ResultSet rs = preparedStatement.executeQuery();
				while (rs.next()) {

					u = new Usuario();
					tu = new TipoUsuario();

					u.setNome(rs.getString("Nome"));
					u.setSenha(rs.getString("Senha"));
					u.setCpf(rs.getLong("Cpf"));
					u.setDataNascimento(rs.getDate("Nascimento"));
					u.setLogin(rs.getString("Login"));
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

	public boolean insertTipo(Usuario u) {
		// TODO Auto-generated method stub
		Connection cf = ConnectionFactory.getConnection();
		java.sql.Date dataSql = new Date(u.getDataCadastro().getTime());
		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "INSERT INTO USUARIO_TIPO (FK_TIPO_USUARIO, FK_CPF, DATA_CADASTRO) VALUES (?, ?, ?)";

			PreparedStatement preparedStatement;
			try {
				preparedStatement = cf.prepareStatement(selectSQL);

				preparedStatement.setInt(1, u.getTipoUsuario().getId());
				preparedStatement.setLong(2, u.getCpf());
				preparedStatement.setDate(3, dataSql);

				// preparedStatement.setString(1, "Pedro Henrique Banali");
				int rs = preparedStatement.executeUpdate();

				cf.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}
}
