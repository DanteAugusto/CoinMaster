package gcm.coinmaster.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gcm.coinmaster.model.Conta;
import gcm.coinmaster.model.ContaPoupanca;
import gcm.coinmaster.model.ContaBonus;
import gcm.coinmaster.model.DTO.TransferenciaDTO;
import gcm.coinmaster.repository.ContaRepository;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta cadastrarConta(String numero) {
        Conta conta = new Conta();
        conta.setNumero(numero);
        conta.setSaldo(0.0);
        return contaRepository.save(conta);
    }

    public ContaBonus cadastrarContaBonus(String numero) {
        ContaBonus contaBonus = new ContaBonus();
        contaBonus.setNumero(numero);
        contaBonus.setSaldo(0);
        contaBonus.setPontuacao(10);

        return contaRepository.save(contaBonus);
    }

    public TransferenciaDTO fazerTransferencia(String origem, String destino, double valor) {
        Conta contaOrigem = contaRepository.findById(origem).orElse(null);
        Conta contaDestino = contaRepository.findById(destino).orElse(null);

        if (contaOrigem != null && contaDestino != null && contaOrigem.getSaldo() >= valor) {
            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaRepository.save(contaOrigem);

            if(contaDestino instanceof ContaBonus contaDestinoBonus) {
                contaDestinoBonus.setPontuacao(contaDestinoBonus.getPontuacao() + (int) valor/200);
                contaDestinoBonus.setSaldo(contaDestinoBonus.getSaldo() + valor);
                contaRepository.save(contaDestinoBonus);
            } else {
                contaDestino.setSaldo(contaDestino.getSaldo() + valor);
                contaRepository.save(contaDestino);
            }

        }

        return new TransferenciaDTO(contaOrigem, contaDestino,valor);
    }
    public Conta porCredito(String numero, double credito) {
        Conta conta = contaRepository.findById(numero).orElse(null);
        if (conta != null) {
            if(conta instanceof ContaBonus contaBonus) {
                contaBonus.setPontuacao(contaBonus.getPontuacao() + (int) credito/100);
                contaBonus.setSaldo(contaBonus.getSaldo() + credito);
                contaRepository.save(contaBonus);
            } else {
                conta.setSaldo(conta.getSaldo() + credito);
                contaRepository.save(conta);
            }
        }
        return conta;
    }

    public double consultarSaldo(String numero) {
        Conta conta = contaRepository.findById(numero).orElse(null);
        if(conta != null) {
            return conta.getSaldo();
        } else {
            return 0;
        }
    }

    public Conta debito(String numero, double valor) {
        Conta conta = contaRepository.findById(numero).orElse(null);
        if(conta != null && conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            contaRepository.save(conta);
        }
        return conta;
    }

    public ContaPoupanca cadastrarContaPoupanca(String numero) {
        ContaPoupanca conta = new ContaPoupanca();
        conta.setNumero(numero);
        conta.setSaldo(0.0);
        return contaRepository.save(conta);
    }

    public ContaPoupanca renderJuros(String numero, double taxa) {
        Conta conta = contaRepository.findById(numero).orElse(null);
        if(conta != null && conta instanceof ContaPoupanca contaPoupanca){
            contaPoupanca.setSaldo(contaPoupanca.getSaldo() + (contaPoupanca.getSaldo()*taxa)/100);
            contaRepository.save(contaPoupanca);
            return contaPoupanca;
        }
        return null;
    }

    public List<ContaPoupanca> renderJurosTodos(double taxa) {
        List<Conta> contas = contaRepository.findAll();
        List<ContaPoupanca> retorno = new ArrayList<>();

        for (Conta conta : contas) {
            ContaPoupanca jurosRendido = renderJuros(conta.getNumero(), taxa);
            if(jurosRendido!=null)
                retorno.add(jurosRendido);
        }

        return retorno;
    }
}

