package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.dto.jogador.JogadorDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.JogadorEntity;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailService {


    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")

    private String from;

    private final JavaMailSender emailSender;

    //=================================== E-MAIL JOGADOR CADASTRADO===================================

    public void sendEmailJogadorCadastrado(JogadorDTO jogadorDTO, JogadorEntity jogadorCadastrado) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(jogadorDTO.getEmail());
            mimeMessageHelper.setSubject(jogadorDTO.getNomeJogador() + ", o Reino aguardava por você!");
            mimeMessageHelper.setText(buscarConteudoJogadorCadastrado(jogadorDTO, jogadorCadastrado), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }


    // MÉTODO QUE BUSCA NO DTO OS DADOS QUE SERÃO INSERIDOS NO TEMPLATE DO EMAIL
    public String buscarConteudoJogadorCadastrado(JogadorDTO jogadorDTO, JogadorEntity jogadorCadsatrado)
            throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();

        dados.put("nome", "Olá, " + jogadorDTO.getNomeJogador() + "! Finalmente você chegou para salvar o reino!");
        dados.put("mensagem", "Seja bem-vindo(a)! " +
                "Você foi cadastrado(a) com sucesso em Battle of Worlds, seu id no jogo é "
                + jogadorCadsatrado.getIdJogador());
        dados.put("email", "Qualquer dúvida, nos contate através do e-mail " + from);

        Template template = fmConfiguration.getTemplate("email-template.html");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }


    //=================================== E-MAIL JOGADOR EDITADO ===================================

    public void sendEmailJogadorEditado(JogadorDTO jogadorDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(jogadorDTO.getEmail());
            mimeMessageHelper.setSubject(jogadorDTO.getNomeJogador() + ", parece que seus dados mudaram...");
            mimeMessageHelper.setText(buscarConteudoJogadorEditado(jogadorDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    // MÉTODO QUE BUSCA NO DTO OS DADOS QUE SERÃO INSERIDOS NO TEMPLATE DO EMAIL
    public String buscarConteudoJogadorEditado(JogadorDTO jogadorDTO) throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();

        dados.put("nome", "Olá, " + jogadorDTO.getNomeJogador());
        dados.put("mensagem", "Seus dados foram atualizados com sucesso! ");
        dados.put("email", "Qualquer dúvida, nos contate através do e-mail " + from);

        Template template = fmConfiguration.getTemplate("email-template.html");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }

    //=================================== E-MAIL JOGADOR EXCLUÍDO ===================================

    public void sendEmailJogadorExcluido(JogadorDTO jogadorDTO) {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(jogadorDTO.getEmail());
            mimeMessageHelper.setSubject(jogadorDTO.getNomeJogador() + ", você está nos deixando?");
            mimeMessageHelper.setText(buscarConteudoJogadorExcluido(jogadorDTO), true);

            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    // MÉTODO QUE BUSCA NO DTO OS DADOS QUE SERÃO INSERIDOS NO TEMPLATE DO EMAIL
    public String buscarConteudoJogadorExcluido(JogadorDTO jogadorDTO) throws IOException, TemplateException {

        Map<String, Object> dados = new HashMap<>();

        dados.put("nome", jogadorDTO.getNomeJogador() + ", o Reino sentirá falta da sua presença ...");
        dados.put("mensagem", "Seu cadastro foi excluído com sucesso, mas você pode voltar quando quiser! ");
        dados.put("email", "Qualquer dúvida, nos contate através do e-mail " + from);

        Template template = fmConfiguration.getTemplate("email-template.html");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
    }
}
