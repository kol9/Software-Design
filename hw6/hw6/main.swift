//
//  main.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 04.12.2021.
//

import Foundation

func parseEndEvaluate(_ str: String) {
    let tokenizer = Tokenizer()
    let parserVisitor = ParserVisitor()
    let printVisitor1 = PrintVisitor()
    let printVisitor2 = PrintVisitor()
    let calcVisitor = CalcVisitor()

    tokenizer.tokenize(str)
    if tokenizer.state is TokenizerStateError {
        print("Error occured in tokenizing")
        return
    }
    tokenizer.tokens.forEach { $0.accept(printVisitor1) }
    tokenizer.tokens.forEach { $0.accept(parserVisitor) }
    print()
    switch parserVisitor.rpnTokens {
    case .error(let e):
        print("Error: \(e)")
        return
    case .rpnTokens(let rpn):
        rpn.forEach { $0.accept(printVisitor2) }
        rpn.forEach { $0.accept(calcVisitor) }
        print()
        switch calcVisitor.result {
        case .error(let e):
            print("Error: \(e)")
        case .value(let v):
            print("Result is \(v)")
        }
    }
}

parseEndEvaluate("(2+2+2+2)/2")

