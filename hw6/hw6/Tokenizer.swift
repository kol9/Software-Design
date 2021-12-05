//
//  Tokenizer.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 04.12.2021.
//

import Foundation

protocol TokenizerStateProtocol {
    func process(_ c: Character?, with tokenizer: Tokenizer)
}

class TokenizerStateStart: TokenizerStateProtocol {
    func process(_ c: Character?, with tokenizer: Tokenizer) {
        guard let c = c else {
            tokenizer.state = TokenizerStateEnd()
            return
        }
        if c.isWhitespace {
            return
        }
        let strValue = "\(c)"
        
        if let op = OperationToken(rawValue: strValue) {
            tokenizer.tokens.append(op)
            return
        }
        
        if let extraToken = ExtraToken(rawValue: strValue) {
            tokenizer.tokens.append(extraToken)
            return
        }
        
        if let intValue = Int(strValue) {
            tokenizer.state = TokenizerStateNumber(startDigit: intValue)
            return
        }
        
        tokenizer.state = TokenizerStateError()
        tokenizer.tokens = []
    }
}

class TokenizerStateNumber: TokenizerStateProtocol {
    var value: Int
    
    init(startDigit: Int) {
        self.value = startDigit
    }
    
    func process(_ c: Character?, with tokenizer: Tokenizer) {
        guard let c1 = c else {
            tokenizer.tokens.append(ScalarToken.intValue(v: self.value))
            tokenizer.state = TokenizerStateStart()
            tokenizer.state.process(c, with: tokenizer)
            return
        }
        let strValue = "\(c1)"
        
        if let intValue = Int(strValue) {
            self.value = self.value * 10 + intValue
            return
        }
        
        tokenizer.tokens.append(ScalarToken.intValue(v: self.value))
        tokenizer.state = TokenizerStateStart()
        tokenizer.state.process(c, with: tokenizer)
    }
}

class TokenizerStateEnd: TokenizerStateProtocol {
    func process(_ c: Character?, with tokenizer: Tokenizer) { }
}


class TokenizerStateError: TokenizerStateProtocol {
    func process(_ c: Character?, with tokenizer: Tokenizer) { }
}

public class Tokenizer {
    var state: TokenizerStateProtocol = TokenizerStateStart()
    var tokens: [ArithToken] = []
    
    public func tokenize(_ input: String) {
        for c in input {
            self.state.process(c, with: self)
        }
        self.state.process(nil, with: self)
    }
}
