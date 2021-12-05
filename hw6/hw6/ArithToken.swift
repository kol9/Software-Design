//
//  ArithToken.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public protocol ArithToken {
    func accept(_ visitor: TokenVisitor)
}

public enum ScalarToken: CustomStringConvertible, ArithToken {
    public func accept(_ visitor: TokenVisitor) {
        visitor.visit(self)
    }
    
    public var description: String {
        switch self {
        case .intValue(let v):
            return "NUMBER(\(v))"
        }
    }
    
    public var value: Int {
        switch self {
        case .intValue(let v):
            return v
        }
    }
    
    case intValue(v: Int)
}

public enum OperationToken: String, CustomStringConvertible, ArithToken {
    public func accept(_ visitor: TokenVisitor) {
        visitor.visit(self)
    }
    
    public var description: String {
        switch self {
        case .add:
            return "ADD"
        case .sub:
            return "SUB"
        case .mul:
            return "MUL"
        case .div:
            return "DIV"
        }
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
            return v1 / v2
        }
    }
}

public enum ExtraToken: String, CustomStringConvertible, ArithToken {
    public func accept(_ visitor: TokenVisitor) {
        visitor.visit(self)
    }
    
    public var description: String {
        switch self {
        case .leftPar:
            return "LP"
        case .rightPar:
            return "RP"
        }
    }
    
    case leftPar = "("
    case rightPar = ")"
}
