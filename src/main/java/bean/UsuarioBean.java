package bean;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import dao.*;
import entidade.*;
import util.JPAUtil;

@ManagedBean
@ViewScoped
public class UsuarioBean {
	//Cadastro
    private Usuario usuario = new Usuario();
    private List<Usuario> usuarios;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Login
    private String login;
    private String senha;
    
    // Cadastro
    public String salvar() {
        if (usuarioDAO.buscarPorLogin(usuario.getLogin()) != null) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login já existe."));
            return null;
        }

        usuarioDAO.salvar(usuario);
        usuario = new Usuario();
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário criado com sucesso!"));
        return "login";
    }

    public List<Usuario> getUsuarios() {
        if (usuarios == null) {
            usuarios = usuarioDAO.Listar();
        }
        return usuarios;
    }
    
    // Login
    public String autenticar() {
        EntityManager em = JPAUtil.criarEntityManager();
        Usuario usuario = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class)
                .setParameter("login", login)
                .getResultStream().findFirst().orElse(null);

        if (usuario == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login inválido"));
            return null;
        }

        if (!usuario.getSenha().equals(senha)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Senha inválida"));
            return null;
        }

        this.usuario = usuario;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Bem-vindo, " + usuario.getLogin()));
        return "opcoes.xhtml?faces-redirect=true";
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
