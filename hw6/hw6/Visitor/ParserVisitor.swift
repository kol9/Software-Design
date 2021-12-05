//
//  ParserVisitor.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public enum ParserResult {
    case rpnTokens(_ tokens: [ArithToken])
    case error(_ e: ArithExpressionError)
    
    var tokens: [ArithToken]? {
        switch self {
        case .rpnTokens(let tokens):
            return tokens
        default:
            return nil
        }
    }
}

public class ParserVisitor: TokenVisitor {
    private var isBroken = false
    private var result: [ArithToken] = []
    private var stack: [ArithToken] = []

    var rpnTokens: ParserResult {
        if isBroken { return .error(.parsingError) }
        while let topElement = stack.popLast() {
            result.append(topElement)
        }
        
        let containsParenthesis = result.contains { $0 is ExtraToken }
        if containsParenthesis {
            return .error(.parsingError)
        }
        return .rpnTokens(result)
    }
    
    public func visit(_ token: ScalarToken) {
        if isBroken { return }
        result.append(token)
    }
    
    public func visit(_ token: ExtraToken) {
        if isBroken { return }
        switch token {
        case .leftPar:
            stack.append(token)
        case .rightPar:
            while true {
                guard
                    stack.count > 0,
                    let topElement = stack.popLast()
                else {
                    isBroken = true
                    return
                }
                
                if
                    let topElement = topElement as? ExtraToken,
                    topElement == .leftPar
                {
                    break
                }
                
                result.append(topElement)
            }
        }
    }
    
    public func visit(_ token: OperationToken) {
        if isBroken { return }
        while true {
            guard
                stack.count > 0,
                let topElement = stack.last
            else {
                break
            }
            
            guard let op = topElement as? OperationToken else {
                break
            }
            
            if op.priority >= token.priority {
                _ = stack.popLast()
                result.append(topElement)
                continue
            }
            
            break
        }
        stack.append(token)
    }
}
