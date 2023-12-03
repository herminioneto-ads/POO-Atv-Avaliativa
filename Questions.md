# 1.
- Retornando uma mensagem de erro:
No método que retorna uma mensagem de erro, a lógica se concentra em retornar uma mensagem descritiva caso a condição de erro seja encontrada.
Exemplo em Java:
```
public class ExemploMensagemErro {
    public static String verificarNumero(int numero) {
        if (numero < 0) {
            return "Erro: O número não pode ser negativo.";
        } else {
            return "Número válido!";
        }
    }

    public static void main(String[] args) {
        int num1 = 10;
        int num2 = -5;

        System.out.println("Número 1: " + verificarNumero(num1));
        System.out.println("Número 2: " + verificarNumero(num2));
    }
}

```

- Retornando um código de erro:
Já no método que retorna um código de erro, a função retorna um valor específico (nesse caso, -1) para indicar que ocorreu um erro durante o processamento.
Exemplo em Java:
```
public class ExemploCodigoErro {
    public static int dividirNumeros(int numerador, int denominador) {
        try {
            int resultado = numerador / denominador;
            return resultado;
        } catch (ArithmeticException e) {
            return -1; // Código de erro para divisão por zero
        }
    }

    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 0;

        int resultado = dividirNumeros(num1, num2);
        if (resultado == -1) {
            System.out.println("Erro: Divisão por zero!");
        } else {
            System.out.println("Resultado: " + resultado);
        }
    }
}

```

- Blocos try-catch:
Este é um dos métodos mais utilizados para lidar com exceções em Java. Aqui está um exemplo simples que divide dois números e lida com a possível exceção de divisão por zero:
```
public class ExemploTryCatch {
    public static void main(String[] args) {
        int numerador = 10;
        int denominador = 0;
        try {
            int resultado = numerador / denominador;
            System.out.println("Resultado: " + resultado);
        } catch (ArithmeticException e) {
            System.out.println("Erro: Divisão por zero.");
            // Aqui você pode lidar com o erro de acordo com sua lógica
        }
    }
}

```

# 2.
Retornar uma mensagem de erro pode ter limitações porque a mensagem é uma string fixa que pode não ser facilmente processada automaticamente por outros sistemas, dificultando a identificação precisa do erro e sendo menos padronizada para tratamento automatizado de erros. Já retornar códigos de erro pode ser limitante porque exige interpretação correta dos códigos, levando a confusões sobre a natureza do erro, dificultando a manutenção e documentação e potencialmente resultando em tratamento inadequado devido à ambiguidade dos códigos. Os blocos try-catch também podem ter limitações, pois podem aumentar a complexidade do código ao lidar com muitas exceções, tornando-o menos legível. Além disso, o uso excessivo de try-catch para fluxo normal de controle pode diminuir a performance do programa. Em certos casos, capturar exceções muito genéricas pode mascarar problemas específicos, tornando mais difícil identificar a causa raiz de um erro.