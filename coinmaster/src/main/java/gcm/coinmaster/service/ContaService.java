package gcm.coinmaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gcm.coinmaster.model.Conta;
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

    public Conta porCredito(String numero, double credito) {
        Conta conta = contaRepository.findById(numero).orElse(null);
        if (conta != null) {
            conta.setSaldo(conta.getSaldo() + credito);
            contaRepository.save(conta);
        }
        return conta;
    }
}

