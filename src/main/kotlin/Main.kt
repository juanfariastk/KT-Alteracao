fun main() {
    val repositorioAnimal = RepositorioAnimal()
    var opcao = 0
    while (opcao != 11) {
        menu()
        println("Digite a opção: ")
        opcao = readLine()?.toIntOrNull() ?: 0
        when (opcao) {
            1, 2, 3 -> criarAnimal(opcao, repositorioAnimal)
            4 -> repositorioAnimal.listar()
            5 -> repositorioAnimal.animais.forEach(Animal::emitirSom)
            6 -> {
                println("Digite o nome do animal:")
                val nomeDoBicho = readLine() ?: ""
                repositorioAnimal.remover(nomeDoBicho)
            }
            7 -> {
                println("Digite a cor do animal:")
                val corDoBicho = readLine()?.toUpperCase() ?: ""
                repositorioAnimal.listarPorCor(corDoBicho)
            }
            8 -> {
                println("Digite a idade do animal:")
                val idade = readLine()?.toIntOrNull() ?: 0
                repositorioAnimal.listarPorIdade(idade)
            }
            9 -> {
                println("Digite o nome do animal:")
                val nome = readLine() ?: ""
                val animalEncontrado: Animal? = repositorioAnimal.listarPorNome(nome)
                if (animalEncontrado != null) {
                    println("Animal encontrado: ${animalEncontrado.nome}")
                } else {
                    println("Animal não encontrado.")
                }
            }
            10 -> {
                println("Adicionando uma nova pessoa...")
                criarPessoa(repositorioAnimal)
            }
            11 -> println("Saindo...")
            else -> println("Opção inválida!")
        }
    }
}

fun criarAnimal(opcao: Int, repositorio: RepositorioAnimal) {
    println("Digite o nome do animal:")
    val nome = readLine() ?: ""
    println("Digite a idade do animal:")
    val idade = readLine()?.toIntOrNull() ?: 0
    println("Digite a cor do animal (BRAW, BRAWFLASWMSES, BREK, WUAITI):")
    val cor = Color.valueOf(readLine()?.toUpperCase() ?: "BRAW")

    val animal = when (opcao) {
        1 -> Cachorro(idade).apply {
            this.nome = nome
            this.corDoBicho = cor
        }
        2 -> Gato(idade).apply {
            this.nome = nome
            this.corDoBicho = cor
        }
        3 -> Passaro(idade).apply {
            this.nome = nome
            this.corDoBicho = cor
        }
        else -> null
    }
    animal?.let { repositorio.adicionar(it) }
}

fun criarPessoa(repositorio: RepositorioAnimal) {
    println("Digite o nome da pessoa:")
    val nome = readLine() ?: ""
    println("Digite a idade da pessoa:")
    val idade = readLine()?.toIntOrNull() ?: 0

    val pessoa = Homem(idade).apply {
        this.nome = nome
    }
    repositorio.adicionar(pessoa)
    println("Pessoa adicionada com sucesso: ${pessoa.nome}")
}

fun menu() {
    println("\n-- Menu --")
    println("1 - Adicionar Cachorro")
    println("2 - Adicionar Gato")
    println("3 - Adicionar Pássaro")
    println("4 - Listar Animais")
    println("5 - Emitir som de todos os animais")
    println("6 - Remover Animal por nome")
    println("7 - Listar Animais por Cor")
    println("8 - Listar Animais por Idade")
    println("9 - Buscar Animal por Nome")
    println("10 - Adicionar Pessoa")
    println("11 - Sair")
}

enum class Color {
    BRAW, BRAWFLASWMSES, BREK, WUAITI
}

abstract class Animal(var idade: Int) {
    abstract var nome: String
    abstract var corDoBicho: Color

    abstract fun idadeEmAnosHumanos(): Int
    abstract fun emitirSom()

    open fun dadosAnimal() {
        println("Nome: $nome \nCor: $corDoBicho \nIdade: $idade")
    }
}

class Homem(idade: Int) : Animal(idade) {
    override var nome: String = ""
        get() = field
        set(valor) {
            field = valor
        }

    override var corDoBicho: Color = Color.BRAW
        get() = field
        set(valor) {
            field = valor
        }

    override fun idadeEmAnosHumanos(): Int = idade

    override fun emitirSom() {
        println("Olá, eu sou um ser humano e não faço sons de animais.")
    }

    override fun dadosAnimal() {
        println("Nome: $nome \nIdade: $idade \nEu sou um ser humano, não um animal.")
    }
}


class Cachorro(idade: Int) : Animal(idade) {
    override var nome: String = ""
    override var corDoBicho: Color = Color.BRAW

    override fun idadeEmAnosHumanos(): Int = idade * 7
    override fun emitirSom() {
        println("Au au")
    }
}

class Gato(idade: Int) : Animal(idade) {
    override var nome: String = ""
    override var corDoBicho: Color = Color.BRAW

    override fun idadeEmAnosHumanos(): Int = idade * 7
    override fun emitirSom() {
        println("Miau")
    }
}

class Passaro(idade: Int) : Animal(idade) {
    override var nome: String = ""
    override var corDoBicho: Color = Color.BRAW

    override fun idadeEmAnosHumanos(): Int = idade * 7
    override fun emitirSom() {
        println("Piu piu")
    }
}

class RepositorioAnimal {
    val animais: MutableList<Animal> = mutableListOf()

    fun adicionar(animal: Animal) {
        animais.add(animal)
    }

    fun listar() {
        if (animais.isEmpty()) {
            println("Nenhum animal cadastrado.")
            return
        }
        animais.forEach { it.dadosAnimal() }
    }

    fun remover(nome: String) {
        val it = animais.iterator()
        var found = false
        while (it.hasNext()) {
            val animal = it.next()
            if (animal.nome == nome) {
                it.remove()
                println("Animal $nome removido com sucesso.")
                found = true
                break
            }
        }
        if (!found) {
            println("Animal não encontrado.")
        }
    }

    fun listarPorCor(cor: String) {
        val corEnum = try { Color.valueOf(cor) } catch (e: IllegalArgumentException) { null }
        val animaisEncontrados = animais.filter { it.corDoBicho == corEnum }
        if (animaisEncontrados.isEmpty()) {
            println("Não foram encontrados animais com a cor $cor.")
            return
        }
        animaisEncontrados.forEach { it.dadosAnimal() }
    }

    fun listarPorIdade(idade: Int) {
        val animaisEncontrados = animais.filter { it.idade == idade }
        if (animaisEncontrados.isEmpty()) {
            println("Não foram encontrados animais com a idade $idade anos.")
            return
        }
        animaisEncontrados.forEach { it.dadosAnimal() }
    }

    fun listarPorNome(nome: String): Animal? {
        return animais.find { it.nome == nome }
    }
}
