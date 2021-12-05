//
//  CalcVisitor.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public enum CalcResult {
    case value(_ v: Int)
    case error(_ e: ArithExpressionError)
}

public class CalcVisitor: TokenVisitor {
    private var error: ArithExpressionError?
    private var stack: [ScalarToken] = []
    
    public var result: CalcResult {
        guard stack.count == 1 else {
            return .error(.evaluatingError)
        }
        return .value(stack[0].value)
    }
    
    public func visit(_ token: ScalarToken) {
        if error != nil { return }
        stack.append(token)
    }
    
    public func visit(_ token: ExtraToken) {
        if error != nil { return }
        error = .customError("Parenthesis is not supported in RPN notation")
    }
    
    public func visit(_ token: OperationToken) {
        if error != nil { return }
        
        guard
            let v1 = stack.popLast(),
            let v2 = stack.popLast()
        else {
            error = .evaluatingError
            return
        }
        
        let result = ScalarToken.intValue(v: token.apply(v2, v1))
        stack.append(result)
    }
}
