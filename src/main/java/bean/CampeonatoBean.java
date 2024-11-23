package bean;

import dao.CampeonatoDAO;
import entidade.Campeonato;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class CampeonatoBean {

    private Campeonato campeonato = new Campeonato();
    private CampeonatoDAO campeonatoDAO = new CampeonatoDAO();
    
    public void salvar() {
        campeonatoDAO.salvar(campeonato);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Campeonato salvo com sucesso!"));
        campeonato = new Campeonato();
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }
}