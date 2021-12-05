//
//  main.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 04.12.2021.
//

import Foundation

let str = "(5+*4)/3"

let tokenizer = Tokenizer()

tokenizer.tokenize(str)

print(tokenizer.tokens)

let parserVisitor = ParserVisitor()

tokenizer.tokens.forEach { $0.accept(parserVisitor) }

if let tokens = parserVisitor.rpnTokens.tokens {
    print(tokens)
    let calcVisitor = CalcVisitor()
    tokens.forEach { $0.accept(calcVisitor) }
    
    print(calcVisitor.result)
}


