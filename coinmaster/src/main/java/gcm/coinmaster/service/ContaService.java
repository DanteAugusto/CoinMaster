package gcm.coinmaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gcm.coinmaster.model.Conta;
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

    public TransferenciaDTO fazerTransferencia(String origem, String destino, double valor) {
        if (valor < 0) {
            return null;
        }

        Conta contaOrigem = contaRepository.findById(origem).orElse(null);
        Conta contaDestino = contaRepository.findById(destino).orElse(null);

        if (contaOrigem != null && contaDestino != null && contaOrigem.getSaldo() >= valor) {
            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);
            contaRepository.save(contaOrigem);
            contaRepository.save(contaDestino);
        }

        return new TransferenciaDTO(contaOrigem, contaDestino,valor);
    }
    public Conta porCredito(String numero, double credito) {
        if (credito < 0) {
            return null;
        }

        Conta conta = contaRepository.findById(numero).orElse(null);
        if (conta != null) {
            conta.setSaldo(conta.getSaldo() + credito);
            contaRepository.save(conta);
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
        if (valor < 0) {
            return null;
        }
        
        Conta conta = contaRepository.findById(numero).orElse(null);
        if(conta != null && conta.getSaldo() >= valor) {
            conta.setSaldo(conta.getSaldo() - valor);
            contaRepository.save(conta);
        }
        return conta;
    }
}

