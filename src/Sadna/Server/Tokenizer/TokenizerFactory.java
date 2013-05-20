package Sadna.Server.Tokenizer;

public interface TokenizerFactory<T> {
   MessageTokenizer<T> create();
}
