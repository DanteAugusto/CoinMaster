package gcm.coinmaster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gcm.coinmaster.model.Conta;
import gcm.coinmaster.model.ContaBonus;
import gcm.coinmaster.model.ContaPoupanca;
import gcm.coinmaster.model.DTO.ContaDTO;
import gcm.coinmaster.repository.ContaRepository;
import gcm.coinmaster.service.ContaService;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {
    @Mock private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @Nested
    class CadastrarConta{

        @Test
        void testarCadastrarConta() {
            String numero = "12345";
            Double saldo = 1000.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.save(any())).thenReturn(conta);
            assertEquals(conta, contaService.cadastrarConta(numero, saldo));
        }

        @Test
        void testarCadastrarContaBonus() {
            String numero = "12345";
            ContaBonus contaBonus = new ContaBonus();
            contaBonus.setNumero(numero);
            contaBonus.setSaldo(0.0);
            contaBonus.setPontuacao(10);
            when(contaRepository.save(any())).thenReturn(contaBonus);
            assertEquals(contaBonus, contaService.cadastrarContaBonus(numero));
        }

        @Test
        void testarCadastrarContaPoupanca() {
            String numero = "12345";
            double saldo = 10.0;
            ContaPoupanca contaPoupanca = new ContaPoupanca();
            contaPoupanca.setNumero(numero);
            contaPoupanca.setSaldo(saldo);
            when(contaRepository.save(any())).thenReturn(contaPoupanca);
            assertEquals(contaPoupanca, contaService.cadastrarContaPoupanca(numero, saldo));
        }

    }

    @Nested
    class ConsultarConta{

        @Test
        void testarConsultarConta() {
            String numero = "12345";
            double saldo = 1000.0;
            Conta conta = new Conta(numero, saldo);
            ContaDTO contaDTO = new ContaDTO(conta);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(contaDTO, contaService.consultarConta(numero));
        }

        @Test
        void testarConsultarContaBonus() {
            String numero = "12345";
            ContaBonus contaBonus = new ContaBonus();
            contaBonus.setNumero(numero);
            contaBonus.setSaldo(0.0);
            contaBonus.setPontuacao(10);
            ContaDTO contaDTO = new ContaDTO(contaBonus);
            when(contaRepository.findById(any())).thenReturn(Optional.of(contaBonus));
            assertEquals(contaDTO, contaService.consultarConta(numero));
        }

        @Test
        void testarConsultarContaPoupanca() {
            String numero = "12345";
            double saldo = 10.0;
            ContaPoupanca contaPoupanca = new ContaPoupanca();
            contaPoupanca.setNumero(numero);
            contaPoupanca.setSaldo(saldo);
            ContaDTO contaDTO = new ContaDTO(contaPoupanca);
            when(contaRepository.findById(any())).thenReturn(Optional.of(contaPoupanca));
            assertEquals(contaDTO, contaService.consultarConta(numero));
        }

    }

    @Nested
    class ConsultarSaldo{
        @Test
        void testarConsultarSaldo() {
            String numero = "12345";
            Double saldo = 1000.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo, contaService.consultarSaldo(numero));
        }        
    }

    @Nested
    class Credito{
        
        @Test
        void TestarCredito(){
            String numero = "12345";
            double saldo = 500.0;
            double credito = 200.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo+credito, contaService.porCredito(numero, credito).getSaldo());            
        }

        @Test
        void TestarCreditoNegativo(){
            String numero = "12345";
            double credito = -200.0;
            assertEquals(null, contaService.porCredito(numero, credito));            
        }

        // FIXME: tem que corrigir esse teste
        @Test
        void TestarCreditoContaBonus(){
            String numero = "12345";
            double saldo = 500.0;
            double credito = 200.0;
            ContaBonus contaBonus = new ContaBonus();
            contaBonus.setNumero(numero);
            contaBonus.setSaldo(saldo);
            contaBonus.setPontuacao(0);
            when(contaRepository.findById(any())).thenReturn(Optional.of(contaBonus));
            System.out.println("contaBonus: ");
            System.out.println("numero" + contaBonus.getNumero());
            System.out.println("saldo" + contaBonus.getSaldo());
            System.out.println("pontuacao" + contaBonus.getPontuacao());
            System.out.println("contaService.porCredito(numero, credito): ");
            System.out.println("numero" + contaService.porCredito(numero, credito).getNumero());
            System.out.println("saldo" + contaService.porCredito(numero, credito).getSaldo());
            assertEquals(contaBonus, contaService.porCredito(numero, credito));            
        }
    }

    @Nested
    class Debito{
        
        @Test
        void TestarDebito(){
            String numero = "12345";
            double saldo = 500.0;
            double debito = 200.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo-debito, contaService.debito(numero, debito).getSaldo());            
        }

        @Test
        void TestarDebitoNegativo(){
            String numero = "12345";
            double debito = -200.0;
            assertEquals(null, contaService.debito(numero, debito));            
        }

        // O saldo pode ficar até -1000.0, pois é o limite de crédito
        @Test
        void TestarDebitoMaiorQueSaldoMaisMil(){
            String numero = "12345";
            double saldo = 500.0;
            double debito = 12000.0;
            Conta conta = new Conta(numero, saldo);
            when(contaRepository.findById(any())).thenReturn(Optional.of(conta));
            assertEquals(saldo, contaService.debito(numero, debito).getSaldo());            
        }
    }
    
    @Nested
    class Transferencia{}

    @Nested
    class RenderJuros{}
}
