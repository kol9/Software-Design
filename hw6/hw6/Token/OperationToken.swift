//
//  OperationToken.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public enum OperationToken: String, ArithToken {
    public func accept(_ visitor: TokenVisitor) {
        visitor.visit(self)
    }
        
    case add = "+"
    case sub = "-"
    case mul = "*"
    case div = "/"
    
    var priority: Int {
        switch self {
        case .add, .sub:
            return 10
        case .mul, .div:
            return 20
        }
    }
    
    func apply(_ s1: ScalarToken,_ s2: ScalarToken) -> Int {
        let v1 = s1.value
        let v2 = s2.value
        
        switch self {
        case .add:
            return v1 + v2
        case .sub:
            return v1 - v2
        case .mul:
            return v1 * v2
        case .div:
            if v2 == 0 {
                return 0
            }
            return v1 / v2
        }
    }
}
