//
//  ArithToken.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 05.12.2021.
//

import Foundation

public protocol ArithToken { }

public enum ScalarToken: CustomStringConvertible, ArithToken {
    public var description: String {
        switch self {
        case .intValue(let v):
            return "V(\(v))"
        }
    }
    
    case intValue(v: Int)
}

public enum OperationToken: String, CustomStringConvertible, ArithToken {
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
}

public enum ExtraToken: String, CustomStringConvertible, ArithToken {
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
